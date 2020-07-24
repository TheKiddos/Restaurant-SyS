package org.thekiddos.manager.services;

import org.thekiddos.manager.api.model.ItemDTO;

import java.util.List;

public interface ItemService {
    List<ItemDTO> getItems();
}
