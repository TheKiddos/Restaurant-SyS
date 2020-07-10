package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

/**
 * This transaction is used to add an {@link org.thekiddos.manager.models.Item} to
 * an active reservation, so it means the customer has ordered something.
 */
public class AddItemsToReservationTransaction extends AddItemsToServiceTransaction {
    private final Reservation reservation;

    public AddItemsToReservationTransaction( Long tableId ) {
        reservation = Database.getCurrentReservationByTableId( tableId );
        if ( reservation == null || !reservation.isActive() )
            throw new IllegalArgumentException( "No Current Reservations for the selected table" );
        super.service = reservation;
    }

    @Override
    void saveToDatabase() {
        Database.addReservation( reservation );
    }
}
