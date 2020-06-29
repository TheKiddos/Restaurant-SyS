package org.thekiddos.manager;

import com.jfoenix.controls.JFXButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class Util {
    public static URL getResource( String fileName) {
        ClassLoader classLoader = Util.class.getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return resource;
        }
    }

    public static JFXButton createButton( String label, String graphicPath ) {
        JFXButton button = new JFXButton( label );
        if ( graphicPath != null ) {
            ImageView orderImage = new ImageView( new Image( Util.getResource( graphicPath ).toExternalForm() ) );
            orderImage.setFitHeight( 32 );
            orderImage.setFitWidth( 32 );
            button.setGraphic( orderImage );
        }
        return button;
    }
}
