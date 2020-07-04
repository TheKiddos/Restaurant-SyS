package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.payroll.models.Employee;
import org.thekiddos.manager.payroll.models.HourlyClassification;
import org.thekiddos.manager.payroll.models.TimeCard;
import org.thekiddos.manager.payroll.transactions.AddTimeCardTransaction;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@FxmlView("employee.fxml")
public class EmployeeController extends Controller {
    public VBox root;
    public JFXButton addEmployeeButton;
    public TableView<Employee> employeeTable;
    public TableColumn<Employee, Long> employeeIdColumn;
    public TableColumn<Employee, String> employeeNameColumn;
    public TableColumn<Employee, String> employeePaymentClassificationColumn;
    public TableColumn<Employee, String> employeePaymentScheduleColumn;
    public TableColumn<Employee, String> employeePaymentMethodColumn;
    public JFXButton removeEmployeeButton;
    public JFXTextField searchEmployeeField;
    public JFXButton setTimeCardButton;
    public JFXTimePicker hoursWorkedTimePicker;
    public JFXButton viewTimeCardsButton;

    public void initialize() {
        hoursWorkedTimePicker.set24HourView( true );
        hoursWorkedTimePicker.setValidators( new RequiredFieldValidator() );

        employeeIdColumn.setCellValueFactory( new PropertyValueFactory<>( "id" ) );
        employeeNameColumn.setCellValueFactory( new PropertyValueFactory<>( "name" ) );
        employeePaymentClassificationColumn.setCellValueFactory( new PropertyValueFactory<>( "paymentClassification" ) );
        employeePaymentScheduleColumn.setCellValueFactory( new PropertyValueFactory<>( "paymentSchedule" ) );
        employeePaymentMethodColumn.setCellValueFactory( new PropertyValueFactory<>( "paymentMethod" ) );

        searchEmployeeField.textProperty().addListener( ( observable, oldValue, newValue ) -> fillEmployeeTable( newValue ) );
        removeEmployeeButton.disableProperty().bind( employeeTable.getSelectionModel().selectedItemProperty().isNull() );

        fillEmployeeTable( "" );
        employeeTable.getSelectionModel().selectedItemProperty().addListener( this::disableButtonsOnWrongWrongEmployee );
    }

    private void disableButtonsOnWrongWrongEmployee( Observable observable, Employee oldValue, Employee newValue ) {
        if ( newValue == null ) {
            setTimeCardButton.setDisable( true );
            viewTimeCardsButton.setDisable( true );
            return;
        }
        if ( newValue.getPaymentClassification().getType().equals( "Hourly Employee" ) ) {
            setTimeCardButton.setDisable( false );
            viewTimeCardsButton.setDisable( false );
        } else {
            setTimeCardButton.setDisable( true );
            viewTimeCardsButton.setDisable( true );
        }
    }

    private void fillEmployeeTable( String filter ) {
        employeeTable.getItems().clear();
        List<Employee> employeeList = filterEmployees( Database.getEmployees(), filter );
        for ( Employee employee : employeeList ) {
            employeeTable.getItems().add( employee );
        }
    }

    private List<Employee> filterEmployees( List<Employee> employeeList, String filter ) {
        return employeeList.stream().filter( employee ->
                        employee.getName().toLowerCase().contains( filter.toLowerCase() ) ||
                        employee.getId().toString().contains( filter ) ||
                        employee.getPaymentClassification().toString().contains( filter ) ||
                        employee.getPaymentSchedule().toString().contains( filter )
                )
                .collect( Collectors.toList() );
    }

    @Override
    public Node getRoot() {
        return root;
    }

    public void addEmployee( ActionEvent actionEvent ) {
        Stage addEmployeeStage = Util.getWindowContainer( "Add Employee" ).getStage();
        addEmployeeStage.setOnCloseRequest( e -> fillEmployeeTable( searchEmployeeField.getText() ) ); // TODO make sure this is never null
        addEmployeeStage.show();
    }

    public void removeEmployee( ActionEvent actionEvent ) {
        Employee employee = employeeTable.getSelectionModel().getSelectedItem();
        Database.removeEmployeeById( employee.getId() );
        fillEmployeeTable( "" );
    }

    public void setTimeCard( ActionEvent actionEvent ) {
        if ( !hoursWorkedTimePicker.validate() )
            return;

        new AddTimeCardTransaction( employeeTable.getSelectionModel().getSelectedItem().getId(), LocalDate.now(), hoursWorkedTimePicker.getValue() ).execute();
    }

    public void viewTimeCards( ActionEvent actionEvent ) {
        Employee employee = employeeTable.getSelectionModel().getSelectedItem();
        HourlyClassification hourlyClassification = (HourlyClassification) employee.getPaymentClassification();
        TimeCard timeCard = hourlyClassification.getTimeCard( LocalDate.now() );
        String timeWorked = timeCard == null ? "0" : timeCard.getTimeWorked().getHour() + ":" + timeCard.getTimeWorked().getMinute();
        Util.createAlert( "Time Worked", timeWorked, getScene().getWindow(), ButtonType.OK ).showAndWait();
    }
}
