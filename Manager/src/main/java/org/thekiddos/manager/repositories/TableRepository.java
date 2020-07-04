package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.thekiddos.manager.models.Table;

public interface TableRepository extends CrudRepository<Table, Long> {
}
