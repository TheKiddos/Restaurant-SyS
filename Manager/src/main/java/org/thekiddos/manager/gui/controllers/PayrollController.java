package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.gui.models.WindowContainer;
import org.thekiddos.manager.payroll.models.Employee;
import org.thekiddos.manager.payroll.models.PayCheck;
import org.thekiddos.manager.payroll.transactions.PayDayTransaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@FxmlView("payroll.fxml")
public class PayrollController extends Controller {
    public VBox root;
    public JFXTextField searchPayCheckField;
    public TableView<PayCheck> payCheckTable;
    public TableColumn<PayCheck, Employee> employeeDetailsColumn;
    public TableColumn<PayCheck, Double> payCheckAmountColumn;
    public TableColumn<PayCheck, LocalDate> payCheckDateColumn;
    public TableColumn<PayCheck, String> payCheckDetailsColumn;
    public JFXButton removePayCheckButton;
    public JFXButton printPayCheckButton;
    public JFXButton savePayCheckButton;
    public JFXButton getPayChecksButton;
    private List<PayCheck> payChecks = new ArrayList<>();

    public void initialize() {
        employeeDetailsColumn.setCellValueFactory( new PropertyValueFactory<>( "employee" ) );
        payCheckAmountColumn.setCellValueFactory( new PropertyValueFactory<>( "amount" ) );
        payCheckDateColumn.setCellValueFactory( new PropertyValueFactory<>( "payDate" ) );
        payCheckDetailsColumn.setCellValueFactory( new PropertyValueFactory<>( "details" ) );

        searchPayCheckField.textProperty().addListener( ( observable, oldValue, newValue ) -> fillPayCheckTable( newValue ) );

        removePayCheckButton.disableProperty().bind( payCheckTable.getSelectionModel().selectedItemProperty().isNull() );
        printPayCheckButton.disableProperty().bind( payCheckTable.getSelectionModel().selectedItemProperty().isNull() );
        savePayCheckButton.disableProperty().bind( payCheckTable.getSelectionModel().selectedItemProperty().isNull() );
    }

    private void fillPayCheckTable( String filter ) {
        payCheckTable.getItems().clear();
        List<PayCheck> filteredPayChecks = filterPayChecks( filter );
        for ( PayCheck payCheck : filteredPayChecks ) {
            payCheckTable.getItems().add( payCheck );
        }
    }

    private List<PayCheck> filterPayChecks( String filter ) {
        return payChecks.stream().filter( payCheck ->
                payCheck.getEmployee().getName().toLowerCase().contains( filter.toLowerCase() ) ||
                        payCheck.getEmployee().getId().toString().contains( filter ) ||
                        payCheck.getEmployee().getPaymentClassification().toString().contains( filter ) ||
                        payCheck.getEmployee().getPaymentSchedule().toString().contains( filter ) ||
                        String.valueOf( payCheck.getAmount() ).contains( filter )
        )
                .collect( Collectors.toList() );
    }

    @Override
    public Node getRoot() {
        return root;
    }

    public void removePayCheck( ActionEvent actionEvent ) {
        PayCheck selectedPayCheck = payCheckTable.getSelectionModel().getSelectedItem();
        payChecks.remove( selectedPayCheck );
    }

    public void printPayCheck( ActionEvent actionEvent ) {
        PayCheck selectedPayCheck = payCheckTable.getSelectionModel().getSelectedItem();
        WindowContainer payCheckWindow = Util.getWindowContainer( "Pay Check" );
        PayCheckController payCheckController = (PayCheckController) payCheckWindow.getController();
        payCheckController.setPayCheck( selectedPayCheck );
        payCheckWindow.getStage().setResizable( false );
        payCheckWindow.getStage().showAndWait();
        Util.tryPrintPayCheck();
    }

    public void savePayCheck( ActionEvent actionEvent ) {
        // TODO add transaction later
    }

    public void getPayChecks( ActionEvent actionEvent ) {
        PayDayTransaction payDayTransaction = new PayDayTransaction( LocalDate.now() );
        payDayTransaction.execute();
        payChecks = payDayTransaction.getPayChecks();

        fillPayCheckTable( searchPayCheckField.getText() );
    }
}
