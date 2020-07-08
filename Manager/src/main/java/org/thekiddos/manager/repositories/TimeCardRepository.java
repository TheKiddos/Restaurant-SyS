package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.thekiddos.manager.payroll.models.TimeCard;
import org.thekiddos.manager.payroll.models.TimeCardId;

@Repository
public interface TimeCardRepository extends CrudRepository<TimeCard, TimeCardId> {
}
