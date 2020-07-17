package org.thekiddos.manager.gui.controllers;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

public abstract class Controller {
    @Setter @Getter
    private Scene scene;

    public abstract Node getRoot();

    public void closeWindow() {
        Stage stage = (Stage)getScene().getWindow();
        invokeCloseEvent( stage );
        stage.close();
    }

    private void invokeCloseEvent( Stage stage ) {
        stage.getOnCloseRequest().handle( null );
    }

    public abstract void refresh();
}
