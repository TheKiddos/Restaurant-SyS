package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.thekiddos.manager.gui.validator.EmployeeIdValidator;
import org.thekiddos.manager.gui.validator.MoneyValidator;
import org.thekiddos.manager.payroll.transactions.AddEmployeeTransaction;
import org.thekiddos.manager.payroll.transactions.AddHourlyEmployeeTransaction;
import org.thekiddos.manager.payroll.transactions.AddSalariedEmployeeTransaction;

@Component
@FxmlView("add_employee.fxml")
public class AddEmployeeController extends Controller {
    public JFXTextField idField;
    public JFXTextField nameField;
    public ChoiceBox<String> employeePaymentClassificationChoiceBox;
    public JFXTextField paymentField;
    public GridPane root;

    public void initialize() {
        idField.setValidators( new EmployeeIdValidator(), new RequiredFieldValidator() );
        nameField.setValidators( new RequiredFieldValidator() );
        paymentField.setValidators( new MoneyValidator(), new RequiredFieldValidator() );

        employeePaymentClassificationChoiceBox.getItems().add( "Salaried" );
        employeePaymentClassificationChoiceBox.getItems().add( "Hourly" );
        employeePaymentClassificationChoiceBox.getSelectionModel().select( 0 );
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {

    }

    public void addEmployee( ActionEvent actionEvent ) {
        if ( !validateEmployee() )
            return;

        getAddEmployeeTransaction().execute();
        closeWindow();
    }

    private boolean validateEmployee() {
        return idField.validate() && nameField.validate() && paymentField.validate();
    }

    private AddEmployeeTransaction getAddEmployeeTransaction() {
        Long id = Long.parseLong( idField.getText() );
        String name = nameField.getText();
        double payment = Double.parseDouble( paymentField.getText() );

        AddEmployeeTransaction addEmployeeTransaction;
        if ( isSalariedChosen() )
            addEmployeeTransaction = new AddSalariedEmployeeTransaction( id, name, payment );
        else
            addEmployeeTransaction = new AddHourlyEmployeeTransaction( id, name, payment );

        return addEmployeeTransaction;
    }

    private boolean isSalariedChosen() {
        return employeePaymentClassificationChoiceBox.getSelectionModel().getSelectedIndex() == 0;
    }

    public void changeClassification( ActionEvent actionEvent ) {
        String prompt = isSalariedChosen() ? "Salary" : "Hourly Rate";
        paymentField.setPromptText( prompt );
    }
}
