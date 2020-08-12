package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

/**
 * This transaction is used to add an {@link org.thekiddos.manager.models.Item} to
 * an active reservation, so it means the customer has ordered something.
 */
public class AddItemsToReservationTransaction extends AddItemsToServiceTransaction {
    private final Reservation reservation;

    /**
     * Creates the transaction
     * @param tableId the tableId to add the items to
     * @throws IllegalArgumentException if the table doesn't exists or if it's not active
     */
    public AddItemsToReservationTransaction( Long tableId ) {
        reservation = Database.getCurrentReservationByTableId( tableId );
        if ( reservation == null || !reservation.isActive() )
            throw new IllegalArgumentException( "No Current Reservations for the selected table" );
        super.service = reservation;
    }

    /**
     * Updates the reservation
     */
    @Override
    void saveToDatabase() {
        Database.addReservation( reservation );
    }
}
