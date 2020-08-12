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
}
