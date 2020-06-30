package org.thekiddos.manager.gui.controllers;

import javafx.scene.Node;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

public abstract class Controller {
    @Setter @Getter
    private Scene scene;

    public abstract Node getRoot();
}
