package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import javafx.event.ActionEvent;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;

public class GUIController extends Controller {
    public JFXSpinner reservedTableTracker;
    public JFXButton addReservationButton;
    public VBox currentReservations;
    private Stage addReservationGUIStage;

    public void initialize() {
        reservedTableTracker.setTooltip( new Tooltip( "Reserved Tables" ) );
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
        addReservationGUIStage.show();
    }

    public void setAddReservationGUIStage( Stage addReservationGUIStage ) {
        addReservationGUIStage.setOnCloseRequest( e -> refresh() );
        this.addReservationGUIStage = addReservationGUIStage;
    }

    private void refresh() {
        System.out.println("new stuff");
    }
}
