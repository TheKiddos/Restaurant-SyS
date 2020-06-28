package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUIController extends Controller {
    public JFXSpinner reservedTableTracker;
    public JFXButton addReservationButton;
    public VBox currentReservations;
    private Stage addReservationGUIStage;

    public void addReservation( ActionEvent actionEvent ) {
        addReservationGUIStage.show();
    }

    private void refresh() {
        System.out.println("new stuff");
    }

    public void setAddReservationGUIStage( Scene scene ) {
        addReservationGUIStage = new Stage();
        addReservationGUIStage.initOwner( getScene().getWindow() );
        addReservationGUIStage.setTitle( "Add Reservation" );
        addReservationGUIStage.initModality( Modality.NONE );
        addReservationGUIStage.initStyle( StageStyle.UNDECORATED );
        addReservationGUIStage.setOnCloseRequest( e -> refresh() );
        addReservationGUIStage.setScene( scene );
    }
}
