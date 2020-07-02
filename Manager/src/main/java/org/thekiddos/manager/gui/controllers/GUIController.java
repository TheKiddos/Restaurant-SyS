package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.gui.Remover;
import org.thekiddos.manager.gui.models.WindowContainer;
import org.thekiddos.manager.gui.validator.CustomerIdValidator;
import org.thekiddos.manager.gui.validator.FeeValidator;
import org.thekiddos.manager.gui.validator.PositiveIntegerValidator;
import org.thekiddos.manager.gui.validator.TableIdValidator;
import org.thekiddos.manager.gui.views.ReservationPane;
import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.models.Table;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddCustomerTransaction;
import org.thekiddos.manager.transactions.AddTableTransaction;
import org.thekiddos.manager.transactions.DeleteItemTransaction;

import java.time.LocalDate;
import java.util.List;

public class GUIController extends Controller implements Remover {
    public JFXSpinner reservedTableTracker;
    public JFXButton addReservationButton;
    public VBox currentReservationsBox;
    public JFXTabPane root;

    public JFXButton addTableButton;
    public JFXButton removeTableButton;
    public TableView<Table> tableTable;
    public TableColumn<Table, Long> tableIdColumn;
    public TableColumn<Table, Integer> tableMaxCapacityColumn;
    public TableColumn<Table, Double> tableFeeColumn;
    public JFXTextField tableIdField;
    public JFXTextField tableMaxCapacityField;
    public JFXTextField tableFeeField;

    public JFXButton addCustomerButton;
    public TableView<Customer> customerTable;
    public TableColumn<Customer, Long> customerIdColumn;
    public TableColumn<Customer, String> customerFirstNameColumn;
    public TableColumn<Customer, String> customerLastNameColumn;
    public JFXButton removeCustomerButton;
    public JFXTextField customerIdField;
    public JFXTextField customerFirstNameField;
    public JFXTextField customerLastNameField;

    public JFXButton addItemButton;
    public TableView<Item> itemTable;
    public TableColumn<Item, Long> itemIdColumn;
    public TableColumn<Item, String> itemNameColumn;
    public TableColumn<Item, Double> itemPriceColumn;
    public TableColumn<Item, String> itemDescriptionColumn;
    public JFXButton removeItemButton;

    private Stage addReservationGUIStage;
    private WindowContainer orderWindow;
    // TODO fix this annoying refresh behavior of closing all ReservationPanes
    // TODO add search bar for tables
    // TODO Alerts
    public void initialize() {
        initializeMainGUI();
        initializeTableGUI();
        initializeCustomerGUI();
        initializeItemGUI();
    }

    @Override
    public Node getRoot() {
        return root;
    }

    // Main GUI
    private void initializeMainGUI() {
        orderWindow = Util.getWindowContainer( "Order Summary" );
        orderWindow.getStage().setOnCloseRequest( e -> refreshMainGUI() );
        reservedTableTracker.setTooltip( new Tooltip( "Reserved Tables" ) );
        // TODO do I need the remover?
        currentReservationsBox.getChildren().add( new ReservationPane( Database.getReservationsByTableId( 1L ).get( 0 ), this, orderWindow ) );
        updateReservedTableTracker();
    }

    private void refreshMainGUI() {
        ReservationPane.resetCounter();
        currentReservationsBox.getChildren().clear();
        List<Reservation> currentReservations = Database.getCurrentReservations();
        for ( Reservation reservation : currentReservations )
            currentReservationsBox.getChildren().add( new ReservationPane( reservation, this, orderWindow ) );
        updateReservedTableTracker();
    }

    private void updateReservedTableTracker() {
        int numberOfTables = Database.getTables().size();
        int numberOfReservedTables = numberOfTables - Database.getFreeTablesOn( LocalDate.now() ).size();
        double ratioOfReservedTables = ((double)numberOfReservedTables) / numberOfTables;
        reservedTableTracker.setProgress( ratioOfReservedTables );
    }

    // TODO no need for lazy anymore but keeping it just in case.
    public void addReservation( ActionEvent actionEvent ) {
        if ( addReservationGUIStage == null )
            setAddReservationGUIStage();
        addReservationGUIStage.show();
    }

    private void setAddReservationGUIStage() {
        addReservationGUIStage = Util.getWindowContainer( "Add Reservation" ).getStage();
        addReservationGUIStage.setOnCloseRequest( e -> refreshMainGUI() );
    }

    @Override
    public void remove( Node node ) {
        currentReservationsBox.getChildren().remove( node );
        refreshMainGUI();
    }

    // Table GUI
    private void initializeTableGUI() {
        tableIdField.setValidators( new TableIdValidator(), new RequiredFieldValidator() );
        tableMaxCapacityField.setValidators( new PositiveIntegerValidator(), new RequiredFieldValidator() );
        tableFeeField.setValidators( new FeeValidator(), new RequiredFieldValidator() );

        tableIdColumn.setCellValueFactory( new PropertyValueFactory<>( "id" ) );
        tableMaxCapacityColumn.setCellValueFactory( new PropertyValueFactory<>( "maxCapacity" ) );
        tableFeeColumn.setCellValueFactory( new PropertyValueFactory<>( "tableFee" ) );

        removeTableButton.disableProperty().bind( tableTable.getSelectionModel().selectedItemProperty().isNull() );

        fillTableTableView();
    }

    private void fillTableTableView() {
        tableTable.getItems().clear();
        for ( Long tableId : Database.getTables() ) {
            Table table = Database.getTableById( tableId );
            tableTable.getItems().add( table );
        }
    }

    public void addTable( ActionEvent actionEvent ) {
        if ( !validateTableFields() )
            return;

        Long id = Long.valueOf( tableIdField.getText() );
        int maxCapacity = Integer.parseInt( tableMaxCapacityField.getText() );
        double fee = Double.parseDouble( tableFeeField.getText() );

        AddTableTransaction addTableTransaction = new AddTableTransaction(id, maxCapacity );
        addTableTransaction.setTableFee( fee );
        addTableTransaction.execute();

        fillTableTableView();
    }

    private boolean validateTableFields() {
        return
                tableIdField.validate() &&
                        tableMaxCapacityField.validate() &&
                        tableFeeField.validate();
    }

    public void removeTable( ActionEvent actionEvent ) {
        Table table = tableTable.getSelectionModel().getSelectedItem();
        Database.removeTableById( table.getId() );

        fillTableTableView();
    }

    // Customer GUI
    private void initializeCustomerGUI() {
        customerIdField.setValidators( new CustomerIdValidator(), new RequiredFieldValidator() );
        customerFirstNameField.setValidators( new RequiredFieldValidator() );
        customerLastNameField.setValidators( new RequiredFieldValidator() );

        customerIdColumn.setCellValueFactory( new PropertyValueFactory<>( "id" ) );
        customerFirstNameColumn.setCellValueFactory( new PropertyValueFactory<>( "firstName" ) );
        customerLastNameColumn.setCellValueFactory( new PropertyValueFactory<>( "lastName" ) );

        removeCustomerButton.disableProperty().bind( customerTable.getSelectionModel().selectedItemProperty().isNull() );

        fillCustomerTableView();
    }

    private void fillCustomerTableView() {
        customerTable.getItems().clear();
        for ( Long customerId : Database.getCustomers() ) {
            Customer customer = Database.getCustomerById( customerId );
            customerTable.getItems().add( customer );
        }
    }

    public void addCustomer( ActionEvent actionEvent ) {
        if ( !validateCustomerFields() )
            return;

        Long id = Long.parseLong( customerIdField.getText() );
        String firstName = customerFirstNameField.getText();
        String lastName = customerLastNameField.getText();

        new AddCustomerTransaction( id, firstName, lastName ).execute();
        fillCustomerTableView();
    }

    private boolean validateCustomerFields() {
        return customerIdField.validate() && customerFirstNameField.validate() && customerLastNameField.validate();
    }

    public void removeCustomer( ActionEvent actionEvent ) {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        Database.removeCustomerById( customer.getId() );
        fillCustomerTableView();
    }

    // Item GUI
    private void initializeItemGUI() {
        itemIdColumn.setCellValueFactory( new PropertyValueFactory<>( "id" ) );
        itemNameColumn.setCellValueFactory( new PropertyValueFactory<>( "name" ) );
        itemPriceColumn.setCellValueFactory( new PropertyValueFactory<>( "price" ) );
        itemDescriptionColumn.setCellValueFactory( new PropertyValueFactory<>( "description" ) );
        removeItemButton.disableProperty().bind( itemTable.getSelectionModel().selectedItemProperty().isNull() );
        fillItemTableView();
    }

    private void fillItemTableView() {
        itemTable.getItems().clear();
        for ( Item item : Database.getItems() ) {
            itemTable.getItems().add( item );
        }
    }

    public void addItem( ActionEvent actionEvent ) {
        Stage addItemStage = Util.getWindowContainer( "Add Item" ).getStage();
        addItemStage.setOnCloseRequest( e -> fillItemTableView() );
        addItemStage.show();
    }

    public void removeItem( ActionEvent actionEvent ) {
        Long itemId = itemTable.getSelectionModel().getSelectedItem().getId();

        new DeleteItemTransaction( itemId ).execute();
        fillItemTableView();
    }
}
