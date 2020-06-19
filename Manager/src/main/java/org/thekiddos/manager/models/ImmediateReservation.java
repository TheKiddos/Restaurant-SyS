package org.thekiddos.manager.models;

import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class ImmediateReservation extends Reservation {
    public ImmediateReservation( @NonNull Long tableId, @NonNull Long customerId, @NonNull LocalDate reservationDate, @NonNull LocalTime reservationTime ) {
        super( tableId, customerId, reservationDate, reservationTime );
    }
}
