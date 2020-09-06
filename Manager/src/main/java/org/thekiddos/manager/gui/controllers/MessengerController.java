package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("messenger.fxml")
public class MessengerController extends Controller {
    public VBox root;
    public VBox messagesBox;
    public JFXTextArea enteredMessageArea;
    public JFXButton sendMessageButton;

    public void handleEnterKey( KeyEvent keyEvent ) {
        if ( keyEvent.getCode().equals( KeyCode.ENTER ) ) {
            if ( keyEvent.isShiftDown() ) {
                enteredMessageArea.appendText( "\n" );
                return;
            }

            // get rid of pressed Enter
            enteredMessageArea.deletePreviousChar();
            sendMessageButton.fire();
        }
    }

    public void sendMessage( ActionEvent actionEvent ) {

    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {

    }
}
