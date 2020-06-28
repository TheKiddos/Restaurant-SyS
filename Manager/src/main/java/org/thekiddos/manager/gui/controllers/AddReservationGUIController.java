package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddReservationTransaction;
import org.thekiddos.manager.transactions.ImmediateReservationTransaction;
import org.thekiddos.manager.transactions.ScheduledReservationTransaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class AddReservationGUIController extends Controller {
    // TODO use Alert to show errors instead of exceptions

    public ToggleGroup reservationType;
    public ChoiceBox<Long> customerSelector;
    public ChoiceBox<Long> tableSelector;
    public JFXDatePicker reservationDatePicker;
    public JFXTimePicker reservationTimePicker;

    public void initialize() {
        fillTableSelector( LocalDate.now() );
        fillCustomerSelector();
    }

    private void fillTableSelector( LocalDate date ) {
        tableSelector.getItems().clear();
        Set<Long> freeTables = Database.getFreeTablesOn( date );
        tableSelector.getItems().addAll( freeTables );
    }

    private void fillCustomerSelector() {
        customerSelector.getItems().addAll( Database.getCustomers() );
    }

    public void activateImmediateMode( ActionEvent actionEvent ) {
        reservationDatePicker.setDisable( true );
        reservationTimePicker.setDisable( true );
        fillTableSelector( LocalDate.now() );
    }

    public void activateScheduledMode( ActionEvent actionEvent ) {
        reservationDatePicker.setDisable( false );
        reservationTimePicker.setDisable( false );
        tableSelector.getItems().clear();
    }

    public void reserve( ActionEvent actionEvent ) {
        Long tableId = tableSelector.getValue();
        Long customerId = customerSelector.getValue();

        AddReservationTransaction addReservationTransaction = getReservationTransaction( tableId, customerId );

        addReservationTransaction.execute();
        close( actionEvent );
    }

    private AddReservationTransaction getReservationTransaction( Long tableId, Long customerId ) {
        AddReservationTransaction addReservationTransaction;

        if ( isImmediateReservation() ) {
            addReservationTransaction = new ImmediateReservationTransaction( tableId, customerId );
        }
        else {
            LocalDate reservationDate = reservationDatePicker.getValue();
            LocalTime reservationTime = reservationTimePicker.getValue();
            addReservationTransaction =  new ScheduledReservationTransaction( tableId, customerId, reservationDate, reservationTime );
        }

        return addReservationTransaction;
    }

    private boolean isImmediateReservation() {
        return reservationDatePicker.isDisabled();
    }

    public void close( ActionEvent actionEvent ) {
        Stage stage = (Stage)getScene().getWindow();
        stage.getOnCloseRequest().handle( null );
        stage.close();
    }

    public void updateTableSelector( ActionEvent actionEvent ) {
        // TODO should we handle date in the past here too?
        fillTableSelector( reservationDatePicker.getValue() );
    }
}
