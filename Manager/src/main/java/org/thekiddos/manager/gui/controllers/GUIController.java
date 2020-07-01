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
import org.thekiddos.manager.gui.models.WindowContainer;
import org.thekiddos.manager.gui.views.ReservationPane;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.util.List;

public class GUIController extends Controller implements Remover {
    public JFXSpinner reservedTableTracker;
    public JFXButton addReservationButton;
    public VBox currentReservationsBox;
    public JFXTabPane root;
    private Stage addReservationGUIStage;
    private WindowContainer orderWindow;
    // TODO fix this annoying refresh behavior of closing all ReservationPanes
    public void initialize() {
        orderWindow = Util.getWindowContainer( "Order Summary" );
        orderWindow.getStage().setOnCloseRequest( e -> refresh() );

        reservedTableTracker.setTooltip( new Tooltip( "Reserved Tables" ) );
        // TODO do I need the remover?
        currentReservationsBox.getChildren().add( new ReservationPane( Database.getReservationsByTableId( 1L ).get( 0 ), this, orderWindow ) );

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
        ReservationPane.resetCounter();
        currentReservationsBox.getChildren().clear();
        List<Reservation> currentReservations = Database.getCurrentReservations();
        for ( Reservation reservation : currentReservations )
            currentReservationsBox.getChildren().add( new ReservationPane( reservation, this, orderWindow ) );
        updateReservedTableTracker();
    }

    @Override
    public void remove( Node node ) {
        currentReservationsBox.getChildren().remove( node );
        refresh();
    }

    @Override
    public Node getRoot() {
        return root;
    }
}
