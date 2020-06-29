package org.thekiddos.manager.models;

import lombok.Getter;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class Reservation extends Service {
    private final Long tableId;
    private final double reservationFee;
    private boolean active = false;
    private final double tableFee;

    public Reservation( Long tableId, Long customerId, LocalDate reservationDate, LocalTime reservationTime, double reservationFee ) {
        super( customerId, reservationDate, reservationTime );
        this.tableId = tableId;
        this.reservationFee = reservationFee;
        this.tableFee = Database.getTableById( tableId ).getTableFee(); // TODO stored more than once bad?
    }

    @Override
    public double getFees() {
        return reservationFee + tableFee;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        active = true;
    }

    public double getTableFee() {
        return tableFee;
    }
}
