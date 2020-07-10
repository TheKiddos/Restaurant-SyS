package org.thekiddos.manager.gui.models;

import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.thekiddos.manager.gui.controllers.Controller;

@Getter
@RequiredArgsConstructor
public final class WindowContainer {
    @NonNull
    private final Scene scene;
    @NonNull
    private final Stage stage;
    @NonNull
    private final Controller controller;
}
