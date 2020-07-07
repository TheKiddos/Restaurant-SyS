package org.thekiddos.manager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thekiddos.manager.repositories.Database;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@javax.persistence.Table(name = "SITTING_TABLE")
public class Table {
    @Id
    private Long id;
    private int maxCapacity;
    private double tableFee;

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
