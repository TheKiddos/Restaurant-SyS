package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.models.ServiceId;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationsRepository extends CrudRepository<Reservation, ServiceId> {
    List<Reservation> findByServiceIdCustomerId( Long customerId );
    List<Reservation> findByServiceIdDate( LocalDate date );
    List<Reservation> findByTableId( Long tableId );
}
