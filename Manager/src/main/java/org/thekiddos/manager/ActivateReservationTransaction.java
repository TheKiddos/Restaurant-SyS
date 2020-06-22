package org.thekiddos.manager;

import org.thekiddos.manager.models.Reservation;

import java.time.LocalDate;

public class ActivateReservationTransaction implements Transaction {
    private Reservation reservation;

    public ActivateReservationTransaction( Reservation reservation ) {
        this.reservation = reservation;
    }

    @Override
    public void execute() {
        LocalDate now = LocalDate.now();
        if ( !reservation.getDate().equals( now ) )
            throw new IllegalArgumentException( "Can't activate a reservation in another day" );
        reservation.activate();
    }
}
