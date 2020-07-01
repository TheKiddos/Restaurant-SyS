package org.thekiddos.manager.gui.controllers;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.models.Invoice;
import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.repositories.Database;

import java.time.format.DateTimeFormatter;

public class InvoiceController extends Controller {
    public VBox root;
    public Label customerNameLabel;
    public Label tableIdLabel;
    public Label reservationDateTimeLabel;
    public Label totalLabel;
    public Label tableFeeLabel;
    public Label reservationFeeLabel;
    public Label discountLabel;
    public Label netAmountLabel;
    public VBox detailsBox;
    public GridPane ordersGridPane = new GridPane();

    public void initialize() {
        detailsBox.getChildren().add( ordersGridPane );
    }
    // TODO use TableView like order?
    public void setInvoice( Invoice invoice ) {
        Customer customer = Database.getCustomerById( invoice.getCustomerId() );

        customerNameLabel.setText( "Customer Id: " + customer.getId() + "\tCustomer Name: " + customer.getFirstName() + " " + customer.getLastName() );
        tableIdLabel.setText( "Table Id: " + invoice.getTableId() );
        String time = invoice.getTime().format( DateTimeFormatter.ofPattern( "hh:mm a" ) );
        reservationDateTimeLabel.setText( "Reservation Date: " + invoice.getDate() + " At: " + time );
        netAmountLabel.setText( "Net: " + invoice.getNetAmount() );
        tableFeeLabel.setText( "Table Fee: " + invoice.getTableFee() );
        reservationFeeLabel.setText( "Reservation Fee: " + invoice.getReservationFee() );
        discountLabel.setText( "Discount: " + invoice.getDiscount() );
        totalLabel.setText( "Total: " + invoice.getTotal() );

        ordersGridPane.setVgap( 5 );
        ordersGridPane.setHgap( 30 );
        ordersGridPane.getChildren().clear();
        ordersGridPane.add( createLabel( "Item Name" ), 0, 0 );
        ordersGridPane.add( createLabel( "Quantity" ), 1, 0 );
        ordersGridPane.add( createLabel( "Unit Price" ), 2, 0 );

        int row = 1;
        for ( Item item : invoice.getItems().keySet() ) {
            ordersGridPane.add( createLabel( item.getName() ), 0, row );
            ordersGridPane.add( createLabel( String.valueOf( invoice.getItems().get( item ) ) ), 1, row );
            ordersGridPane.add( createLabel( String.valueOf( item.getPrice() ) ), 2, row );
            ++row;
        }
    }

    private Label createLabel( String text ) {
        Label label = new Label( text );
        label.setStyle( "-fx-text-fill: black" );
        label.setFont( Font.font( "Times New Roman", FontWeight.BOLD, 12 ) );
        return label;
    }

    @Override
    public Node getRoot() {
        return root;
    }
}
