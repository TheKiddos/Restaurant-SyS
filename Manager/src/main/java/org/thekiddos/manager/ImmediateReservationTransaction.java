package org.thekiddos.manager;

import org.thekiddos.manager.models.ImmediateReservation;
import org.thekiddos.manager.models.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ImmediateReservationTransaction extends AddReservationTransaction {
    public ImmediateReservationTransaction( Long tableId, Long customerId ) {
        super( tableId, customerId, LocalDate.now(), LocalTime.now() );
    }

    @Override
    protected Reservation getReservation() {
        try {
            return new ImmediateReservation( tableId, customerId, reservationDate, reservationTime );
        }
        catch ( IllegalArgumentException e ) {
            return null;
        }
    }
}
