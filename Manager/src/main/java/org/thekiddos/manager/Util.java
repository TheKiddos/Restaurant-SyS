package org.thekiddos.manager;

import com.jfoenix.controls.JFXButton;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.context.ApplicationContext;
import org.thekiddos.manager.gui.controllers.Controller;
import org.thekiddos.manager.gui.models.WindowContainer;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private static final Map<String, WindowContainer> windows = new HashMap<>();
    public static final Image ICON = new Image( Util.getResource( "static/images/icon.png" ).toExternalForm() );
    public static final String STYLESHEET_PATH = Util.getResource( "static/style.css" ).toExternalForm();
    public static ApplicationContext applicationContext;

    public static URL getResource( String fileName) {
        ClassLoader classLoader = Util.class.getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return resource;
        }
    }

    public static WindowContainer getWindowContainer( String stageTitle ) {
        return windows.get( stageTitle );
    }

    public static void addWindowContainer( String name, WindowContainer window ) {
        windows.put( name, window );
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

    public static <C extends Controller> WindowContainer createWindowContainer( Class<C> controllerClass, Window owner, String title ) {
        FxWeaver fxWeaver = applicationContext.getBean( FxWeaver.class );
        Parent root = fxWeaver.loadView( controllerClass );

        Controller controller = applicationContext.getBean( controllerClass );
        controller.setScene( new Scene( root ) );
        controller.getScene().getStylesheets().add( STYLESHEET_PATH );

        Stage stage = new Stage();
        stage.initOwner( owner );
        stage.setTitle( title );
        stage.setScene( controller.getScene() );
        stage.getIcons().add( ICON );

        WindowContainer windowContainer = new WindowContainer( controller.getScene(), stage, controller );
        addWindowContainer( title, windowContainer );
        return windowContainer;
    }

    public static Alert createAlert( String titleText, String contentText, Window owner, ButtonType... buttons ) {
        Alert alert = new Alert( null, contentText, buttons );
        alert.setTitle( titleText );
        alert.initModality( Modality.WINDOW_MODAL );
        alert.initOwner( owner );
        alert.getDialogPane().getScene().getStylesheets().add( STYLESHEET_PATH );
        alert.getDialogPane().getStyleClass().add( "root" );
        ( (Stage) alert.getDialogPane().getScene().getWindow() ).getIcons().add( ICON );
        return alert;
    }

    // TODO make it create Exceptions
    public static void print( Node node, boolean isPayCheck )
    {
        PrinterJob job = PrinterJob.createPrinterJob();
        Paper paper = Paper.A5;
        PageOrientation pageOrientation = PageOrientation.PORTRAIT;
        if ( isPayCheck ) {
            paper = Paper.LEGAL;
            pageOrientation = PageOrientation.LANDSCAPE;
        }
        PageLayout pageLayout = job.getPrinter().createPageLayout( paper, pageOrientation, Printer.MarginType.HARDWARE_MINIMUM );
        job.getJobSettings().setPageLayout( pageLayout );

        if (job == null)
            return;

        boolean printed = job.printPage(node);
        if ( !printed ) {
            return;
        }

        job.endJob();
    }
}
