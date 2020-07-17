package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
import org.thekiddos.manager.gui.validator.CustomerIdValidator;
import org.thekiddos.manager.gui.validator.EmailValidator;
import org.thekiddos.manager.gui.validator.LengthValidator;
import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddCustomerTransaction;
import org.thekiddos.manager.transactions.DeleteCustomerTransaction;

@Component
@FxmlView("customers.fxml")
public class CustomerController extends Controller {
    public VBox root;
    public JFXButton addCustomerButton;
    public TableView<Customer> customerTable;
    public TableColumn<Customer, Long> customerIdColumn;
    public TableColumn<Customer, String> customerNameColumn;
    public TableColumn<Customer, String> customerEmailColumn;
    public JFXButton removeCustomerButton;
    public JFXTextField customerIdField;
    public JFXTextField customerNameField;
    public JFXTextField customerEmailField;
    public JFXPasswordField customerPasswordField;

    public void initialize() {
        customerIdField.setValidators( new CustomerIdValidator(), new RequiredFieldValidator() );
        customerNameField.setValidators( new RequiredFieldValidator() );
        customerPasswordField.setValidators( new LengthValidator( 8 ),new RequiredFieldValidator() );
        customerEmailField.setValidators( new EmailValidator() );

        customerIdColumn.setCellValueFactory( new PropertyValueFactory<>( "id" ) );
        customerNameColumn.setCellValueFactory( new PropertyValueFactory<>( "name" ) );
        customerEmailColumn.setCellValueFactory( new PropertyValueFactory<>( "email" ) );

        removeCustomerButton.disableProperty().bind( customerTable.getSelectionModel().selectedItemProperty().isNull() );

        fillCustomerTableView();
    }

    private void fillCustomerTableView() {
        customerTable.getItems().clear();
        for ( Long customerId : Database.getCustomersId() ) {
            Customer customer = Database.getCustomerById( customerId );
            customerTable.getItems().add( customer );
        }
    }

    public void addCustomer( ActionEvent actionEvent ) {
        if ( !validateCustomerFields() ) {
            return;
        }

        Long id = Long.parseLong( customerIdField.getText() );
        String name = customerNameField.getText();
        String email = customerEmailField.getText();
        String password = customerPasswordField.getText();

        AddCustomerTransaction addCustomerTransaction = new AddCustomerTransaction( id, name, email, password );
        addCustomerTransaction.execute();
        fillCustomerTableView();
    }

    private boolean validateCustomerFields() {
        return customerIdField.validate() && customerNameField.validate() && customerPasswordField.validate() && customerEmailField.validate();
    }

    public void removeCustomer( ActionEvent actionEvent ) {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        try {
            new DeleteCustomerTransaction( customer.getId() ).execute();
        }
        catch ( IllegalArgumentException ex ) {
            Util.createAlert( "Error", ex.getMessage(), getScene().getWindow(), ButtonType.CLOSE ).showAndWait();
        }
        finally {
            fillCustomerTableView();
        }
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {
        fillCustomerTableView();
    }
}
