package org.thekiddos.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.thekiddos.manager.models.Item;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    /**
     * @param name the name (or part of it) of the item you want to find
     * @return a list of all the {@link Item}s containing the specified parameter in their name
     */
    List<Item> findByNameContaining( String name );

    /**
     * @param description the description (or part of it) of the item you want to find
     * @return a list of all the {@link Item}s containing the specified parameter in their description
     */
    List<Item> findByDescriptionContaining( String description );

    /**
     *
     * @param price the price of the item you want to find
     * @return a list of all the {@link Item}s containing that are priced with the parameter
     */
    List<Item> findByPrice( double price );
}
