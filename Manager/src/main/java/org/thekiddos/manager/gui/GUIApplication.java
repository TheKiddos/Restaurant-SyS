package org.thekiddos.manager.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.thekiddos.manager.Util;

public class GUIApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load( Util.getResource("templates/GUI.fxml") );
        primaryStage.setTitle( "Digital Restaurant Manager" );
        primaryStage.setScene( new Scene( root ) );
        primaryStage.getScene().getStylesheets().add( Util.getResource( "static/style.css" ).toExternalForm() );
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
