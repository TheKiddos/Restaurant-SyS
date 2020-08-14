package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.models.ServiceId;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationsRepository extends CrudRepository<Reservation, ServiceId> {
    /**
     * @param customerId the customer id
     * @return a list of all {@link org.thekiddos.manager.models.Customer}'s {@link Reservation}s
     */
    List<Reservation> findByServiceIdCustomerId( Long customerId );

    /**
     * @param date the date of the reservations
     * @return a list of {@link Reservation} in the specified date
     */
    List<Reservation> findByServiceIdDate( LocalDate date );

    /**
     * @param tableId the tableId
     * @return a list of {@link Reservation} for the specified table
     */
    List<Reservation> findByTableId( Long tableId );

    List<Reservation> findAllByActiveTrue();
}
