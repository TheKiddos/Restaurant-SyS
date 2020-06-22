package org.thekiddos.manager;

import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;

public class ActivateReservationTransaction implements Transaction {
    private Reservation reservation;

    public ActivateReservationTransaction( Long tableId ) {
        reservation = Database.getCurrentReservationByTableId( tableId );
        if ( reservation == null )
            throw new IllegalArgumentException( "DIE" ); // TODO new name
    }

    @Override
    public void execute() {
        LocalDate now = LocalDate.now();
        if ( !reservation.getDate().equals( now ) ) // TODO Check time too bruh
            throw new IllegalArgumentException( "Can't activate a reservation in another day" );
        reservation.activate();
    }
}
