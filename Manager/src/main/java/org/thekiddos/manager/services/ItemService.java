package org.thekiddos.manager.services;

import org.thekiddos.manager.api.model.ItemDTO;
import org.thekiddos.manager.models.Item;

import java.util.List;

public interface ItemService {
    List<ItemDTO> getItems();

    boolean allItemsInMenu( List<Item> orderedItems );
}
