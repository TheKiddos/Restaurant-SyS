package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.gui.validator.MoneyValidator;
import org.thekiddos.manager.gui.validator.PositiveIntegerValidator;
import org.thekiddos.manager.gui.validator.TableIdValidator;
import org.thekiddos.manager.models.Table;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddTableTransaction;
import org.thekiddos.manager.transactions.DeleteTableTransaction;

@Component
@FxmlView("table.fxml")
public class TableController extends Controller {
    public JFXButton addTableButton;
    public JFXButton removeTableButton;
    public TableView<Table> tableTable;
    public TableColumn<Table, Long> tableIdColumn;
    public TableColumn<Table, Integer> tableMaxCapacityColumn;
    public TableColumn<Table, Double> tableFeeColumn;
    public JFXTextField tableIdField;
    public JFXTextField tableMaxCapacityField;
    public JFXTextField tableFeeField;
    public VBox root;

    public void initialize() {
        tableIdField.setValidators( new TableIdValidator(), new RequiredFieldValidator() );
        tableMaxCapacityField.setValidators( new PositiveIntegerValidator(), new RequiredFieldValidator() );
        tableFeeField.setValidators( new MoneyValidator(), new RequiredFieldValidator() );

        tableIdColumn.setCellValueFactory( new PropertyValueFactory<>( "id" ) );
        tableMaxCapacityColumn.setCellValueFactory( new PropertyValueFactory<>( "maxCapacity" ) );
        tableFeeColumn.setCellValueFactory( new PropertyValueFactory<>( "fee" ) );

        removeTableButton.disableProperty().bind( tableTable.getSelectionModel().selectedItemProperty().isNull() );

        fillTableTableView();
    }

    private void fillTableTableView() {
        tableTable.getItems().clear();
        for ( Long tableId : Database.getTablesId() ) {
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
        updateMainGUI();
    }

    private void updateMainGUI() {
        GUIController guiController = (GUIController) Util.getWindowContainer( "Digital Restaurant Manager" ).getController();
        guiController.refreshMainGUI();
    }

    private boolean validateTableFields() {
        return
                tableIdField.validate() &&
                        tableMaxCapacityField.validate() &&
                        tableFeeField.validate();
    }

    public void removeTable( ActionEvent actionEvent ) {
        Table table = tableTable.getSelectionModel().getSelectedItem();

        try {
            new DeleteTableTransaction( table.getId() ).execute();
        }
        catch ( IllegalArgumentException ex ) {
            Util.createAlert( "Error", ex.getMessage(), getScene().getWindow(), ButtonType.CLOSE ).showAndWait();
        }
        finally {
            fillTableTableView();
        }
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {
        if ( tableTable.getItems().size() != Database.getTablesId().size() )
            fillTableTableView();
    }
}
