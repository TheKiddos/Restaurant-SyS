package org.thekiddos.manager.payroll.repositories;

import org.springframework.data.repository.CrudRepository;
import org.thekiddos.manager.payroll.models.TimeCard;
import org.thekiddos.manager.payroll.models.TimeCardId;

public interface TimeCardRepository extends CrudRepository<TimeCard, TimeCardId> {
}
