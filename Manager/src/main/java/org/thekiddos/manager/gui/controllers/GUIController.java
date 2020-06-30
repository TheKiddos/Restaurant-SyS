package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTabPane;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.gui.Remover;
import org.thekiddos.manager.gui.views.ReservationPane;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;

public class GUIController extends Controller implements Remover {
    public JFXSpinner reservedTableTracker;
    public JFXButton addReservationButton;
    public VBox currentReservations;
    public JFXTabPane root;
    private Stage addReservationGUIStage;

    public void initialize() {
        reservedTableTracker.setTooltip( new Tooltip( "Reserved Tables" ) );

        currentReservations.getChildren().add( new ReservationPane( Database.getReservationsByTableId( 1L ).get( 0 ), this, Util.getWindowContainer( "Invoice Summary" ) ) );

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
        addReservationGUIStage.setOnCloseRequest( e -> refresh() );
    }

    private void refresh() {
        System.out.println("new stuff");
    }

    @Override
    public void remove( Node node ) {
        currentReservations.getChildren().remove( node );
    }

    @Override
    public Node getRoot() {
        return root;
    }
}
