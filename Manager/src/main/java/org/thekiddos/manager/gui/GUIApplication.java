package org.thekiddos.manager.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.gui.controllers.Controller;
import org.thekiddos.manager.gui.controllers.GUIController;
import org.thekiddos.manager.transactions.AddCustomerTransaction;
import org.thekiddos.manager.transactions.AddTableTransaction;
import org.thekiddos.manager.transactions.ImmediateReservationTransaction;

public class GUIApplication extends Application {

    @Override
    public void init() throws Exception {
        fillDatabase();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader rootLoader = new FXMLLoader( Util.getResource("templates/GUI.fxml") );
        Parent root = rootLoader.load();

        FXMLLoader addReservationLoader = new FXMLLoader( Util.getResource( "templates/add_reservation.fxml" ) );
        Parent addReservationRoot = addReservationLoader.load();

        GUIController guiController = rootLoader.getController();
        guiController.setScene( new Scene( root ) );
        guiController.getScene().getStylesheets().add( Util.getResource( "static/style.css" ).toExternalForm() );

        Controller addReservationGUIController = addReservationLoader.getController();
        addReservationGUIController.setScene( new Scene( addReservationRoot ) );
        addReservationGUIController.getScene().getStylesheets().add( Util.getResource( "static/style.css" ).toExternalForm() );

        Stage addReservationGUIStage = createAddReservationGUIStage( addReservationGUIController.getScene(), guiController.getScene().getWindow() );
        guiController.setAddReservationGUIStage( addReservationGUIStage );

        primaryStage.getIcons().add( new Image( Util.getResource( "static/images/icon.png" ).toExternalForm() ) );
        primaryStage.setTitle( "Digital Restaurant Manager" );
        primaryStage.setScene( guiController.getScene() );
        primaryStage.show();
    }

    /**
     * This method is for testing purposes only
     */
    void fillDatabase() {
        new AddTableTransaction( 1L ).execute();
        new AddTableTransaction( 2L ).execute();
        new AddTableTransaction( 3L ).execute();
        new AddCustomerTransaction( 1L, "Z", "X" ).execute();
        new AddCustomerTransaction( 2L, "A", "B" ).execute();
        new ImmediateReservationTransaction( 1L, 1L ).execute();
    }

    public Stage createAddReservationGUIStage( Scene scene, Window owner ) {
        Stage addReservationGUIStage = new Stage();
        addReservationGUIStage.initOwner( owner );
        addReservationGUIStage.setTitle( "Add Reservation" );
        addReservationGUIStage.initModality( Modality.NONE );
        addReservationGUIStage.initStyle( StageStyle.UNDECORATED );
        addReservationGUIStage.setScene( scene );
        return addReservationGUIStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
