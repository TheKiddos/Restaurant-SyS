package org.thekiddos.manager.gui.models;

import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.thekiddos.manager.gui.controllers.Controller;

@Getter
@RequiredArgsConstructor
public class WindowContainer {
    @NonNull
    private Scene scene;
    @NonNull
    private Stage stage;
    @NonNull
    private Controller controller;
}
