package org.thekiddos.manager.models;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class Reservation extends Service {
    private Long tableId;
    private double reservationFee;

    public Reservation( Long tableId, Long customerId, LocalDate reservationDate, LocalTime reservationTime, double reservationFee ) {
        super( customerId, reservationDate, reservationTime );
        this.tableId = tableId;
        this.reservationFee = reservationFee;
    }
}
