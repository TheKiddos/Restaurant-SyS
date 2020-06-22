package org.thekiddos.manager.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Table {
    @NonNull
    private Long id;
    private int maxCapacity;
    private double tableFee = 0.0;

    public Table( Long id, int maxCapacity ) {
        this.id = id;
        this.maxCapacity = maxCapacity;
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
