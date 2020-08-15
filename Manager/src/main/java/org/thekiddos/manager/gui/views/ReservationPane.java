package org.thekiddos.manager.gui.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.gui.controllers.InvoiceController;
import org.thekiddos.manager.gui.controllers.OrderController;
import org.thekiddos.manager.gui.models.WindowContainer;
import org.thekiddos.manager.models.Invoice;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.transactions.ActivateReservationTransaction;
import org.thekiddos.manager.transactions.CheckOutTransaction;

/**
 * A {@link TitledPane} that contains a {@link Reservation}
 * It contains all the necessary functions for managing the reservation by the GUI
 */
public final class ReservationPane extends TitledPane {
    private static int reservationNumber = 0;
    private final Reservation reservation;
    private final GridPane content = new GridPane();
    private final Label totalLabel = new Label();
    private final JFXCheckBox isReservationActiveCheckBox = new JFXCheckBox( "Active" );
    private JFXButton viewOrderButton,
            activateButton,
            checkOutButton;
    private final WindowContainer orderWindow;

    public ReservationPane( Reservation reservation ) {
        this.reservation = reservation;
        this.orderWindow = Util.getWindowContainer( "Order Summary" );
        setup();
    }

    private void setup() {
        setText( nextReservationNumber() );
        // First I wanted all reservation panes to be collapsed then
        // when it was being refreshed from the database it collapsed all expanded reservationPanes which got annoying quickly so I commented it
        //setExpanded( false );
        setContent( content );

        disableUserControlOfActivatingAndDeactivatingTheReservation();

        isReservationActiveCheckBox.setSelected( reservation.isActive() );

        totalLabel.setText( String.valueOf( reservation.getTotal() ) );

        createButtons();
        createLayout();
    }

    private void disableUserControlOfActivatingAndDeactivatingTheReservation() {
        isReservationActiveCheckBox.setDisable( true );
    }

    private void createButtons() {
        viewOrderButton = Util.createButton( "View Order", "static/images/order.png" );
        activateButton = Util.createButton( "Customer Arrived", "static/images/arrived.png" );
        checkOutButton = Util.createButton( "Check", "static/images/check.png" );

        if ( isReservationActiveCheckBox.isSelected() )
            activateButton.setDisable( true );
        else {
            viewOrderButton.setDisable( true );
            checkOutButton.setDisable( true );
        }

        viewOrderButton.setOnAction( this::viewOrder );
        activateButton.setOnAction( this::activateReservation );
        checkOutButton.setOnAction( this::checkOut );
    }

    private String nextReservationNumber() {
        ++reservationNumber;
        return "Reservation #" + reservationNumber;
    }

    private void activateReservation( ActionEvent actionEvent ) {
        new ActivateReservationTransaction( reservation.getReservedTableId() ).execute();

        activateButton.setDisable( true );
        viewOrderButton.setDisable( false );
        checkOutButton.setDisable( false );
        isReservationActiveCheckBox.setSelected( true );
    }

    private void viewOrder( ActionEvent actionEvent ) {
        OrderController orderController = (OrderController) orderWindow.getController();
        orderController.setReservation( reservation );
        orderWindow.getStage().show();
    }

    private void checkOut( ActionEvent actionEvent ) {
        CheckOutTransaction checkOutTransaction = new CheckOutTransaction( reservation );
        checkOutTransaction.execute();
        showAndPrintInvoice( checkOutTransaction.getInvoice() );
    }

    private void showAndPrintInvoice( Invoice invoice ) {
        WindowContainer invoiceWindow = Util.getWindowContainer( "Invoice Summary" );
        InvoiceController invoiceController = (InvoiceController) invoiceWindow.getController();
        invoiceController.setInvoice( invoice );
        invoiceWindow.getStage().setResizable( false );
        invoiceWindow.getStage().showAndWait();
        Util.tryPrintNode( invoiceController.getRoot() );
    }

    public static void resetCounter() {
        reservationNumber = 0;
    }

    private void createLayout() {
        content.setHgap( 30 );
        content.setVgap( 30 );
        content.setAlignment( Pos.CENTER );
        content.add( new Label( "Customer Id: " + reservation.getCustomerId() ), 0, 0 );
        content.add( new Label( "Table Id: " + reservation.getReservedTableId() ), 1, 0 );
        content.add( isReservationActiveCheckBox, 2, 0 );
        content.add( new Label( "Reservation Date: " + reservation.getDate() ), 0, 1 );
        content.add( new Label( "Reservation Time: " + reservation.getTime().getHour() + ":" + reservation.getTime().getMinute() ), 1, 1 );
        content.add( new HBox( new Label( "Total: " ), totalLabel ), 2, 1 );
        content.add( viewOrderButton, 0, 2 );
        content.add( activateButton, 1, 2 );
        content.add( checkOutButton, 2, 2 );
    }
}
