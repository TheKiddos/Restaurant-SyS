package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Invoice;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

public class CheckOutTransaction implements Transaction {
    private final Long tableId;
    private final Reservation reservation;
    private Invoice invoice;

    public CheckOutTransaction( Long tableId ) {
        this.tableId = tableId;
        this.reservation = Database.getCurrentReservationByTableId( tableId );
        if ( reservation == null || !reservation.isActive() )
            throw new IllegalArgumentException( "The reservations is't active or doesn't exists" );
    }

    @Override
    public void execute() {
        Database.deleteReservation( tableId, reservation.getDate() );
        invoice = new Invoice( reservation, 0.0 );
    }

    public Invoice getInvoice() {
        return invoice;
    }
}
