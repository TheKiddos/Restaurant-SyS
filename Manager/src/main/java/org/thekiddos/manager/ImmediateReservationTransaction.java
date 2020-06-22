package org.thekiddos.manager;

import org.thekiddos.manager.models.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ImmediateReservationTransaction extends AddReservationTransaction {

    public ImmediateReservationTransaction( Long tableId, Long customerId ) {
        super( tableId, customerId, LocalDate.now(), LocalTime.now() );
        super.reservationFee = 0.0;
    }

    @Override
    protected Reservation getReservation() {
        return new Reservation( tableId, customerId, reservationDate, reservationTime, reservationFee );
    }
}
