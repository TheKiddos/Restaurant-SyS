package org.thekiddos.manager;

import org.thekiddos.manager.models.Invoice;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

public class CheckOutTransaction implements Transaction {
    private Long tableId;
    private Reservation reservation;
    private Invoice invoice;

    public CheckOutTransaction( Long tableId ) {
        this.tableId = tableId;
        this.reservation = Database.getCurrentReservationByTableId( tableId );
        if ( reservation == null || !reservation.isActive() )
            throw new IllegalArgumentException( "Die" ); // TODO change the message
    }

    @Override
    public void execute() {
        Database.deleteReservation( tableId, reservation.getDate() );
        invoice = new Invoice();
        invoice.setCustomerId( reservation.getCustomerId() );
        invoice.setTableId( tableId );
        invoice.setReservationFee( reservation.getReservationFee() );
        invoice.setDiscount( 0.0 ); // TODO implement these
        invoice.setOrderTotal( reservation.getOrder().getTotal() );
        invoice.setTotal( reservation.getTotal() );
        invoice.setTableFee( reservation.getTableFee() );
        invoice.setNetAmount( reservation.getTotal() - 0.0 ); // Discount
        invoice.setItems( reservation.getOrder().getItems() );
        // TODO Print the Invoice
    }

    public Invoice getInvoice() {
        return invoice;
    }
}
