package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.thekiddos.manager.gui.validator.CustomerIdValidator;
import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddCustomerTransaction;

public class CustomerController extends Controller {
    public VBox root;
    public JFXButton addCustomerButton;
    public TableView<Customer> customerTable;
    public TableColumn<Customer, Long> customerIdColumn;
    public TableColumn<Customer, String> customerFirstNameColumn;
    public TableColumn<Customer, String> customerLastNameColumn;
    public JFXButton removeCustomerButton;
    public JFXTextField customerIdField;
    public JFXTextField customerFirstNameField;
    public JFXTextField customerLastNameField;

    public void initialize() {
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
        for ( Long customerId : Database.getCustomersId() ) {
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

    @Override
    public Node getRoot() {
        return root;
    }
}
