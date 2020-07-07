package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.thekiddos.manager.models.Table;

@Repository
public interface TableRepository extends CrudRepository<Table, Long> {
}
