package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.models.ServiceId;

import java.time.LocalDate;
import java.util.List;

public interface ReservationsRepository extends CrudRepository<Reservation, ServiceId> {
    List<Reservation> findByServiceIdCustomerId( Long customerId );
    List<Reservation> findByServiceIdDate( LocalDate date );
    List<Reservation> findByTableId( Long tableId );
}
