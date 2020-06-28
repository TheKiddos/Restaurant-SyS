package org.thekiddos.manager.models;

import lombok.Getter;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Table {
    private final Long id;
    private final int maxCapacity;
    private final double tableFee;

    public Table( Long id, int maxCapacity, double tableFee ) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.tableFee = tableFee;
    }

    public boolean isReserved( LocalDate on ) {
        List<Reservation> tableReservations = Database.getReservationsByTableId( id );

        if ( tableReservations.size() == 0 )
            return false;

        for ( Reservation reservation : tableReservations ) {
            if ( reservation.getDate().equals( on ) )
                return true;
        }

        return false;
    }
}
