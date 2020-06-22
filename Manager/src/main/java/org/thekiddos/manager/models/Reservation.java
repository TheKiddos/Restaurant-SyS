package org.thekiddos.manager.models;

import lombok.Getter;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class Reservation extends Service {
    private Long tableId;
    private double reservationFee;
    private boolean active = false;

    public Reservation( Long tableId, Long customerId, LocalDate reservationDate, LocalTime reservationTime, double reservationFee ) {
        super( customerId, reservationDate, reservationTime );
        this.tableId = tableId;
        this.reservationFee = reservationFee;
    }

    @Override
    protected double getFees() {
        return reservationFee + getTableFee();
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        active = true;
    }

    public double getTableFee() {
        return Database.getTableById( tableId ).getTableFee(); // TODO Cache?
    }
}
