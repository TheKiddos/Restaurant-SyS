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
import org.thekiddos.manager.gui.controllers.Controller;
import org.thekiddos.manager.gui.controllers.PayCheckController;
import org.thekiddos.manager.gui.models.WindowContainer;
import org.thekiddos.manager.repositories.Database;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * This class has several helper methods used throughout the application
 */
public final class Util {
    private static final Map<String, WindowContainer> WINDOWS = new HashMap<>();
    private static final ClassLoader CLASS_LOADER = Util.class.getClassLoader();

    public static final Image ICON = new Image( Util.getResource( "static/images/icon.png" ).toExternalForm() );
    public static final String STYLESHEET_PATH = Util.getResource( "static/style.css" ).toExternalForm();
    public static final String ROOT_STYLE_CLASS = "root";

    /**
     * This method is used to retrieve the URL of a file in the Resources folder
     * @param fileName The resource to retrieve
     * @return A URL for the fileName passed
     * @throws IllegalArgumentException if the file can't be found
     */
    public static URL getResource( String fileName) {
        URL resource = CLASS_LOADER.getResource(fileName);

        if (resource == null)
            throw new IllegalArgumentException("file is not found!");

        return resource;
    }

    /**
     * Used to retrieve any {@link WindowContainer} that was created using the {@link #createWindowContainer(Class, Window, String)} function or manually
     * added using the {@link #addWindowContainer(String, WindowContainer)} function
     * @param name The name used to identify the WindowContainer usually the Stage's title
     * @return The WindowContainer that matches the name provided
     */
    public static WindowContainer getWindowContainer( String name ) {
        return WINDOWS.get( name );
    }

    /**
     * Stores a {@link WindowContainer} so it can be retrieved
     * @param name An identifier for the WindowContainer
     * @param window The WindowContainer to store
     */
    public static void addWindowContainer( String name, WindowContainer window ) {
        WINDOWS.put( name, window );
    }

    /**
     * A helper function to create a {@link JFXButton}
     * @param label The label for the button
     * @param graphicPath The path for the graphic image (it should be stored in the Resources folder or any subfolder of it) or null if no graphics is desired
     *                    The graphic images is scaled to 32 by 32 pixels
     * @return The JFXButton instance created
     */
    public static JFXButton createButton( String label, String graphicPath ) {
        JFXButton button = new JFXButton( label );
        if ( graphicPath != null )
            button.setGraphic( createGraphic( graphicPath ) );
        return button;
    }

    /**
     * Creates an ImageView to be used as a graphics for a node
     * The Image has size of 32 by 32 pixels
     * @param graphicPath The path for the graphic image (it should be stored in the Resources folder or any subfolder of it) or null if no graphics is desired
     * @return The ImageView representing the graphic
     */
    private static ImageView createGraphic( String graphicPath ) {
        ImageView graphic = new ImageView( new Image( Util.getResource( graphicPath ).toExternalForm() ) );
        graphic.setFitHeight( 32 );
        graphic.setFitWidth( 32 );
        return graphic;
    }

    /**
     * Creates a {@link WindowContainer} and stores it with the stage title as an id.
     * This function use default settings like setting up the icon to the default {@link #ICON} field and adding the Stylesheet pointed be the {@link #STYLESHEET_PATH}
     * @param controllerClass The controller class for the WindowContainer
     * @param owner The Owner Window can be null
     * @param title The Title of the stage also used as an id to fetch the WindowContainer later with the {@link #getWindowContainer(String)} function
     * @param <C> Any class that extends the {@link Controller} class
     * @return The created WindowContainer
     */
    public static <C extends Controller> WindowContainer createWindowContainer( Class<C> controllerClass, Window owner, String title ) {
        Parent root = loadFXMLView( controllerClass );
        Scene scene = createScene( root );
        Stage stage = createStage( owner, title, scene );
        Controller controller = Database.getBean( controllerClass );

        WindowContainer windowContainer = new WindowContainer( scene, stage, controller );
        addWindowContainer( title, windowContainer );
        return windowContainer;
    }

    /**
     * Creates a stage with the default settings
     * @param owner The owner window for the stage
     * @param title The Title for the stage
     * @param scene The scene of the stage
     * @return The created stage
     */
    private static Stage createStage( Window owner, String title, Scene scene ) {
        Stage stage = new Stage();
        stage.initOwner( owner );
        stage.setTitle( title );
        stage.setScene( scene );
        stage.getIcons().add( ICON );
        return stage;
    }

    /**
     * Creates a scene with the default configurations (like the stylesheet that the {@link #STYLESHEET_PATH} points to
     * @param root the root node that the scene holds
     * @return the created scene
     */
    private static Scene createScene( Parent root ) {
        Scene scene = new Scene( root );
        scene.getStylesheets().add( STYLESHEET_PATH );
        return scene;
    }

    /**
     * Loads and returns the FXML root node of the provided Controller class
     * @param controllerClass The Controller class for the view to be retrieved
     * @param <C> Any Class that extends the {@link Controller} class.
     * @return The Parent root of the FXML view
     */
    private static <C extends Controller> Parent loadFXMLView( Class<C> controllerClass ) {
        FxWeaver fxWeaver = Database.getBean( FxWeaver.class );
        return fxWeaver.loadView( controllerClass );
    }

    /**
     * Creates an {@link Alert} instance and returns it.
     * The default Modality for the Alert is the {@link javafx.stage.Modality#WINDOW_MODAL}
     * This function use default settings like setting up the icon to the default {@link #ICON} field and adding the Stylesheet pointed be the {@link #STYLESHEET_PATH}
     * This function also assigns the root css cass for styling
     * @param titleText The alert title
     * @param contentText The Alert Content text
     * @param owner The owner window
     * @param buttons Buttons for the alert
     * @return The created alert instance
     */
    public static Alert createAlert( String titleText, String contentText, Window owner, ButtonType... buttons ) {
        Alert alert = new Alert( null, contentText, buttons );
        alert.setTitle( titleText );
        alert.initModality( Modality.WINDOW_MODAL );
        alert.initOwner( owner );
        setStyleSheet( alert );
        setStyleClass( alert );
        setAlertIcon( alert );
        return alert;
    }

    private static void setAlertIcon( Alert alert ) {
        ( (Stage) alert.getDialogPane().getScene().getWindow() ).getIcons().add( ICON );
    }

    private static void setStyleClass( Alert alert ) {
        alert.getDialogPane().getStyleClass().add( ROOT_STYLE_CLASS );
    }

    private static void setStyleSheet( Alert alert ) {
        alert.getDialogPane().getScene().getStylesheets().add( STYLESHEET_PATH );
    }

    // TODO make it create Exceptions

    /**
     * This function is used to print a paycheck from the {@link PayCheckController#getRoot()}
     * it uses a {@link Paper} of size {@link Paper#LEGAL} and {@link PageOrientation#LANDSCAPE}
     * @return true if printing is successful false otherwise
     */
    public static boolean tryPrintPayCheck() {
        Node payCheckRoot = getWindowContainer( "Pay Check" ).getController().getRoot();
        return tryPrint( payCheckRoot, createPageLayout( Paper.LEGAL, PageOrientation.LANDSCAPE ) );
    }

    /**
     * This function is used to print a node
     * it uses a {@link Paper} of size {@link Paper#A5} and {@link PageOrientation#PORTRAIT}
     * @param root The node to be printed
     * @return true if printing is successful false otherwise
     */
    public static boolean tryPrintNode( Node root ) {
        return tryPrint( root, createPageLayout( Paper.A5, PageOrientation.PORTRAIT ) );
    }

    /**
     * This function is used to print a node
     * @param node The node to be printed
     * @param pageLayout the {@link PageLayout} you want to print to.
     * @return true if printing is successful false otherwise
     */
    public static boolean tryPrint( Node node, PageLayout pageLayout ) {
        PrinterJob job = PrinterJob.createPrinterJob();

        if ( job == null )
            return false;

        job.getJobSettings().setPageLayout( pageLayout );

        boolean printed = job.printPage(node);
        if ( !printed )
            return false;

        job.endJob();
        return true;
    }

    public static PageLayout createPageLayout( Paper paper, PageOrientation pageOrientation ) {
        return Printer.getDefaultPrinter().createPageLayout( paper, pageOrientation, Printer.MarginType.HARDWARE_MINIMUM );
    }
}
