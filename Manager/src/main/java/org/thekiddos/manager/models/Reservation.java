package org.thekiddos.manager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thekiddos.manager.repositories.Database;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends Service {
    private double reservationFee;
    private boolean active = false;

    @ManyToOne
    private Table table;

    public Reservation( Long tableId, Long customerId, LocalDate reservationDate, LocalTime reservationTime, double reservationFee ) {
        super( customerId, reservationDate, reservationTime );
        table = Database.getTableById( tableId );
        this.reservationFee = reservationFee;
    }

    @Override
    public double getFees() {
        return reservationFee + table.getTableFee();
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        active = true;
    }

    public double getTableFee() {
        return table.getTableFee();
    }

    public Long getReservedTableId() {
        return table.getId();
    }
}
