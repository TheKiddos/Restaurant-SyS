package org.thekiddos.manager.services;

import org.thekiddos.manager.api.model.ItemDTO;
import org.thekiddos.manager.models.Item;

import java.util.List;

public interface ItemService {
    List<ItemDTO> getItems();

    /**
     * Checks to see if all the supplied {@link Item}s exists in the menu (Database)
     * @param orderedItems A list of items to check
     * @return true if all items are in the menu, false otherwise
     */
    boolean allItemsInMenu( List<Item> orderedItems );

    /**
     * Adds items to the order of the given table.
     * @param tableId - The table number to add the order to.
     * @param orderedItems - A list of items to add.
     * @throws IllegalArgumentException - If the table is not active now, or the items are not in the database.
     */
    void addItemsToOrder( Long tableId, List<Item> orderedItems ) throws IllegalArgumentException;
}
