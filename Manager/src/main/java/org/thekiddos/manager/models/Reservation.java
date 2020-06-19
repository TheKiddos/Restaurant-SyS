package org.thekiddos.manager.models;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public abstract class Reservation {
    private Long tableId;
    private Long customerId;
    private LocalDate reservationDate;
    private LocalTime reservationTime;

    public Reservation( Long tableId, Long customerId, LocalDate reservationDate, LocalTime reservationTime ) {
        this.tableId = tableId;
        this.customerId = customerId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }
}
