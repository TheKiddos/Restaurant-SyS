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
import org.thekiddos.manager.gui.validator.FeeValidator;
import org.thekiddos.manager.gui.validator.PositiveIntegerValidator;
import org.thekiddos.manager.gui.validator.TableIdValidator;
import org.thekiddos.manager.gui.views.ReservationPane;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.models.Table;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddTableTransaction;

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
    public TableView CustomerTable;
    public TableColumn CustomerIdColumn;
    public TableColumn customerFirstNameColumn;
    public TableColumn customerLastName;
    public JFXButton removeCustomerButton;

    public JFXButton addItemButton;
    public TableView itemTable;
    public TableColumn itemIdColumn;
    public TableColumn itemNameColumn;
    public TableColumn itemPriceColumn;
    public TableColumn itemDescriptionColumn;
    public JFXButton removeItemButton;

    private Stage addReservationGUIStage;
    private WindowContainer orderWindow;
    // TODO fix this annoying refresh behavior of closing all ReservationPanes
    // TODO Alerts
    public void initialize() {
        initializeMainGUI();
        initializeTableGUI();
    }

    private void initializeTableGUI() {
        tableIdField.setValidators( new TableIdValidator(), new RequiredFieldValidator() );
        tableMaxCapacityField.setValidators( new PositiveIntegerValidator(), new RequiredFieldValidator() );
        tableFeeField.setValidators( new FeeValidator(), new RequiredFieldValidator() );

        tableIdColumn.setCellValueFactory( new PropertyValueFactory<>( "id" ) );
        tableMaxCapacityColumn.setCellValueFactory( new PropertyValueFactory<>( "maxCapacity" ) );
        tableFeeColumn.setCellValueFactory( new PropertyValueFactory<>( "tableFee" ) );

        fillTableTableView();
    }

    private void fillTableTableView() {
        tableTable.getItems().clear();
        for ( Long tableId : Database.getTables() ) {
            Table table = Database.getTableById( tableId );
            tableTable.getItems().add( table );
        }
    }

    private void initializeMainGUI() {
        orderWindow = Util.getWindowContainer( "Order Summary" );
        orderWindow.getStage().setOnCloseRequest( e -> refreshMainGUI() );
        reservedTableTracker.setTooltip( new Tooltip( "Reserved Tables" ) );
        // TODO do I need the remover?
        currentReservationsBox.getChildren().add( new ReservationPane( Database.getReservationsByTableId( 1L ).get( 0 ), this, orderWindow ) );
        updateReservedTableTracker();
    }

    // TODO this may be optimized once I keep track of all reservations
    private void updateReservedTableTracker() {
        int numberOfTables = Database.getTables().size();
        int numberOfReservedTables = numberOfTables - Database.getFreeTablesOn( LocalDate.now() ).size();
        double ratioOfReservedTables = ((double)numberOfReservedTables) / numberOfTables;
        reservedTableTracker.setProgress( ratioOfReservedTables );
    }

    public void addReservation( ActionEvent actionEvent ) {
        if ( addReservationGUIStage == null )
            setAddReservationGUIStage();
        addReservationGUIStage.show();
    }

    private void setAddReservationGUIStage() {
        addReservationGUIStage = Util.getWindowContainer( "Add Reservation" ).getStage();
        addReservationGUIStage.setOnCloseRequest( e -> refreshMainGUI() );
    }

    private void refreshMainGUI() {
        ReservationPane.resetCounter();
        currentReservationsBox.getChildren().clear();
        List<Reservation> currentReservations = Database.getCurrentReservations();
        for ( Reservation reservation : currentReservations )
            currentReservationsBox.getChildren().add( new ReservationPane( reservation, this, orderWindow ) );
        updateReservedTableTracker();
    }

    @Override
    public void remove( Node node ) {
        currentReservationsBox.getChildren().remove( node );
        refreshMainGUI();
    }

    @Override
    public Node getRoot() {
        return root;
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

    public void addCustomer( ActionEvent actionEvent ) {
    }

    public void removeCustomer( ActionEvent actionEvent ) {
    }

    public void addItem( ActionEvent actionEvent ) {
    }

    public void removeItem( ActionEvent actionEvent ) {
    }
}
