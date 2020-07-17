package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.thekiddos.manager.models.Type;

public interface TypeRepository extends CrudRepository<Type, String> {
}
