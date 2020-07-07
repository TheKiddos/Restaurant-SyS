package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.thekiddos.manager.models.Item;

import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long> {
    Optional<Item> findByNameContaining( String name );
    Optional<Item> findByDescriptionContaining( String description );
    Optional<Item> findByPrice( double price );
}
