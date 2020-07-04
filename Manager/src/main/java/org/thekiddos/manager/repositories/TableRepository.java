package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.thekiddos.manager.models.SittingTable;

@Repository
public interface TableRepository extends CrudRepository<SittingTable, Long> {
}
