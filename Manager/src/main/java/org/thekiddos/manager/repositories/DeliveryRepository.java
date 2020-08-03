package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.thekiddos.manager.models.Delivery;
import org.thekiddos.manager.models.ServiceId;

import java.util.List;

public interface DeliveryRepository extends CrudRepository<Delivery, ServiceId> {
    List<Delivery> findByServiceIdCustomerId( Long customerId );
}
