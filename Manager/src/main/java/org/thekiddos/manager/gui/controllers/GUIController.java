package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTabPane;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.gui.models.WindowContainer;
import org.thekiddos.manager.gui.views.ReservationPane;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.util.List;

@Component
@FxmlView("GUI.fxml")
public class GUIController extends Controller {
    public JFXSpinner reservedTableTracker;
    public JFXButton addReservationButton;
    public VBox currentReservationsBox;
    public JFXTabPane root;
    public Tab tableTab;
    public Tab customerTab;
    public Tab itemTab;
    public Tab employeeTab;
    public Tab payrollTab;
    public Tab deliveriesTab;

    private Stage addReservationGUIStage;

    // TODO fix this annoying refresh behavior of closing all ReservationPanes
    // TODO add search bar for tables
    // TODO Alerts
    public void initialize() {
        initializeMainGUI();
        tableTab.setContent( Util.getWindowContainer( "Tables" ).getController().getRoot() );
        customerTab.setContent( Util.getWindowContainer( "Customers" ).getController().getRoot() );
        itemTab.setContent( Util.getWindowContainer( "Items" ).getController().getRoot() );
        employeeTab.setContent( Util.getWindowContainer( "Employees" ).getController().getRoot() );
        payrollTab.setContent( Util.getWindowContainer( "Payroll" ).getController().getRoot() );
        deliveriesTab.setContent( Util.getWindowContainer( "Delivery" ).getController().getRoot() );
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {
        refreshMainGUI();
    }

    private void initializeMainGUI() {
        WindowContainer orderWindow = Util.getWindowContainer( "Order Summary" );
        orderWindow.getStage().setOnCloseRequest( e -> refreshMainGUI() );
        reservedTableTracker.setTooltip( new Tooltip( "Reserved Tables" ) );
        refreshMainGUI();
    }

    public void refreshMainGUI() {
        List<Reservation> currentReservations = Database.getCurrentReservations();

        if ( currentReservationsBox.getChildren().size() != currentReservations.size() ) {
            ReservationPane.resetCounter();
            currentReservationsBox.getChildren().clear();
            for ( Reservation reservation : currentReservations )
                currentReservationsBox.getChildren().add( new ReservationPane( reservation ) );
            updateReservedTableTracker();
        }
    }

    private void updateReservedTableTracker() {
        int numberOfTables = Database.getTablesId().size();
        int numberOfReservedTables = numberOfTables - Database.getFreeTablesOn( LocalDate.now() ).size();
        double ratioOfReservedTables = ((double)numberOfReservedTables) / numberOfTables;
        reservedTableTracker.setProgress( ratioOfReservedTables );
    }

    // TODO no need for lazy anymore but keeping it just in case.
    public void addReservation( ActionEvent actionEvent ) {
        if ( addReservationGUIStage == null )
            setAddReservationGUIStage();
        addReservationGUIStage.show();
    }

    private void setAddReservationGUIStage() {
        addReservationGUIStage = Util.getWindowContainer( "Add Reservation" ).getStage();
        addReservationGUIStage.setOnCloseRequest( e -> refreshMainGUI() );
    }

    public void openMessenger( ActionEvent actionEvent ) {
        WindowContainer messengerWindow = Util.getWindowContainer( "Messenger" );
        MessengerController messengerController = (MessengerController) messengerWindow.getController();
        messengerController.readMessages();
        messengerWindow.getStage().show();
    }
}
