package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

/**
 * This transaction is used to activate a Scheduled {@link Reservation}.
 * Activating a reservation signature the arrival of a customer.
 * Only reservations that are scheduled for today can be activated obviously
 */
public class ActivateReservationTransaction implements Transaction {
    private final Reservation reservation;

    /**
     * @param tableId The reserved tableId
     * @throws IllegalArgumentException if no reservation for the table specified exists today
     */
    public ActivateReservationTransaction( Long tableId ) {
        reservation = Database.getCurrentReservationByTableId( tableId );
        if ( reservation == null )
            throw new IllegalArgumentException( "No current reservations for the selected table" );
    }

    /**
     * Activates the reservation for the table specified
     */
    @Override
    public void execute() {
        reservation.activate();
        Database.addReservation( reservation );
    }
}
