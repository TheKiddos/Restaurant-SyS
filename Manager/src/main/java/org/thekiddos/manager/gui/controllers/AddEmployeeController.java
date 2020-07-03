package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.thekiddos.manager.gui.validator.EmployeeIdValidator;
import org.thekiddos.manager.gui.validator.MoneyValidator;
import org.thekiddos.manager.payroll.transactions.AddEmployeeTransaction;
import org.thekiddos.manager.payroll.transactions.AddHourlyEmployeeTransaction;
import org.thekiddos.manager.payroll.transactions.AddSalariedEmployeeTransaction;

public class AddEmployeeController extends Controller {
    public JFXTextField idField;
    public JFXTextField nameField;
    public ChoiceBox<String> employeePaymentClassificationChoiceBox;
    public JFXTextField paymentField;
    public GridPane root;

    public void initialize() {
        idField.setValidators( new EmployeeIdValidator(), new RequiredFieldValidator() );
        nameField.setValidators( new RequiredFieldValidator() );
        paymentField.setValidators( new MoneyValidator() );

        employeePaymentClassificationChoiceBox.getItems().add( "Salaried" );
        employeePaymentClassificationChoiceBox.getItems().add( "Hourly" );
        employeePaymentClassificationChoiceBox.getSelectionModel().select( 0 );
    }

    @Override
    public Node getRoot() {
        return root;
    }

    public void addEmployee( ActionEvent actionEvent ) {
        if ( !validate() )
            return;

        AddEmployeeTransaction addEmployeeTransaction = getAddEmployeeTransaction();

        addEmployeeTransaction.execute();
        Stage stage = (Stage)getScene().getWindow();
        stage.getOnCloseRequest().handle( null );
        stage.close();
    }

    private boolean validate() {
        return idField.validate() && paymentField.validate() && paymentField.validate();
    }

    private AddEmployeeTransaction getAddEmployeeTransaction() {
        Long id = Long.parseLong( idField.getText() );
        String name = nameField.getText();
        double payment = Double.parseDouble( paymentField.getText() );

        AddEmployeeTransaction addEmployeeTransaction;
        if ( employeePaymentClassificationChoiceBox.getSelectionModel().getSelectedIndex() == 0 )
            addEmployeeTransaction = new AddSalariedEmployeeTransaction( id, name, payment );
        else
            addEmployeeTransaction = new AddHourlyEmployeeTransaction( id, name, payment );

        return addEmployeeTransaction;
    }

    public void changeClassification( ActionEvent actionEvent ) {
        int selectedIndex = employeePaymentClassificationChoiceBox.getSelectionModel().getSelectedIndex();
        String prompt = selectedIndex == 0 ? "Salary" : "Hourly Rate";
        paymentField.setPromptText( prompt );
    }
}
