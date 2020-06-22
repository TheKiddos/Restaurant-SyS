package org.thekiddos.manager;

import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

public class AddReservationServiceTransaction extends AddServiceTransaction {
    private Reservation reservation;

    public AddReservationServiceTransaction( Long tableId ) {
        super();
        reservation = Database.getCurrentReservationByTableId( tableId );
        if ( reservation == null || !reservation.isActive() )
            throw new IllegalArgumentException( "No Current Reservations for the selected table" );
        super.service = reservation;
    }
}
