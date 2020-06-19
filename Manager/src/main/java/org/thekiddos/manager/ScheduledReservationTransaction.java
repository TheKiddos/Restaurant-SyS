package org.thekiddos.manager;

import lombok.Getter;
import lombok.Setter;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.models.ScheduledReservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduledReservationTransaction extends AddReservationTransaction {
    @Setter
    @Getter
    protected double reservationFee = 10.0;

    public ScheduledReservationTransaction( Long tableId, Long customerId, LocalDate reservationDate, LocalTime reservationTime ) {
        super( tableId, customerId, reservationDate, reservationTime );
    }

    @Override
    protected Reservation getReservation() {
        try {
            return new ScheduledReservation( tableId, customerId, reservationDate, reservationTime, reservationFee );
        }
        catch ( IllegalArgumentException e ) {
            return null;
        }
    }
}
