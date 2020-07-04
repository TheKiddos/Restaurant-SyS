package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.thekiddos.manager.models.SittingTable;

public interface TableRepository extends CrudRepository<SittingTable, Long> {
}
