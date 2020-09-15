package org.thekiddos.manager.gui.views;

import com.jfoenix.controls.JFXTextArea;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.models.Message;

// Only Text messages, further in the future we may support something else
public class MessagePane extends VBox  {
    private static final Image MANAGER_IMAGE = new Image( Util.getResource( "static/images/Manager.png" ).toExternalForm() );
    private static final Image WAITER_IMAGE = new Image( Util.getResource( "static/images/Waiter.png" ).toExternalForm() );
    private static final int MAX_VIEWABLE_ROWS = 5;
    private static final double GRAPHICS_WIDTH = 32;
    private static final double GRAPHICS_HEIGHT = 32;

    private Label header;
    private JFXTextArea body;

    public MessagePane( Message message ) {
        setUpHeader( message.getSender() );
        setUpBody( message );

        setAlignment( Pos.CENTER );
        getChildren().addAll( header, body );
        getStyleClass().add( "message" );
    }

    public void setUpBody( Message message ) {
        body = new JFXTextArea( message.getContents() );
        body.setTooltip( new Tooltip( message.getCreatedAt().toString() ) );
        body.setPrefRowCount( Math.min( MAX_VIEWABLE_ROWS, message.getContents().split( "\n" ).length ) );
        body.setEditable( false );
        body.setFocusColor( Color.TRANSPARENT );
        body.setUnFocusColor( Color.TRANSPARENT );
        body.getStyleClass().add( "message-contents" );
        body.setStyle( "-fx-background-radius: 10px; -fx-padding: 0 10px" );
        // TODO use a PseudoClass instead
        addStyleIfManagerIsSender( message );
    }

    public void addStyleIfManagerIsSender( Message message ) {
        if ( message.getSender().equals( Util.CHAT_USER_MANAGER ) ) {
            body.getStyleClass().add( "right-align" );
            if ( message.isSeen() )
                setSeen();
        }
    }

    public void setUpHeader( String sender ) {
        header = new Label( sender );
        header.setGraphic( getGraphicsFor( sender ) );
        header.getStyleClass().add( "header" );
    }

    private Node getGraphicsFor( String sender ) {
        ImageView graphics = new ImageView( sender.equals( Util.CHAT_USER_MANAGER ) ? MANAGER_IMAGE : WAITER_IMAGE );
        graphics.setFitWidth( GRAPHICS_WIDTH );
        graphics.setFitHeight( GRAPHICS_HEIGHT );
        return graphics;
    }

    public void setSeen() {
        if ( header.getText().equals( Util.CHAT_USER_MANAGER ) )
            body.setStyle( "-fx-background-color: darkgreen; -fx-background-radius: 10px; -fx-padding: 0 15px" );
    }
}
