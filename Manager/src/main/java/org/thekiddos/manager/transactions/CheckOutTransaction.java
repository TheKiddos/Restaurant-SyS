package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Invoice;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

/**
 * Frees the table and produce the invoice
 */
public class CheckOutTransaction implements Transaction {
    private final Long tableId;
    private final Reservation reservation;
    private Invoice invoice;

    /**
     * @throws IllegalArgumentException if the reservation isn't active or doesn't exists
     */
    public CheckOutTransaction( Long tableId ) {
        this.tableId = tableId;
        this.reservation = Database.getCurrentReservationByTableId( tableId );
        if ( reservation == null || !reservation.isActive() )
            throw new IllegalArgumentException( "The reservations is't active or doesn't exists" );
    }

    /**
     * Creates the invoice and deletes the reservation
     */
    @Override
    public void execute() {
        Database.deleteReservation( tableId, reservation.getDate() );
        invoice = new Invoice( reservation, 0.0 );
    }

    /**
     * This can only be called after using the {@link #execute()} method
     * @return The {@link Invoice} for the reservation
     */
    public Invoice getInvoice() {
        return invoice;
    }
}
