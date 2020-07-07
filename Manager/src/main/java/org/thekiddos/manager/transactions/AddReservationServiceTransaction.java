package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

public class AddReservationServiceTransaction extends AddServiceTransaction {
    private final Reservation reservation;

    public AddReservationServiceTransaction( Long tableId ) {
        super();
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
