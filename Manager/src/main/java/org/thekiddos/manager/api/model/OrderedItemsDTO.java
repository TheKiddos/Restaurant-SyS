package org.thekiddos.manager.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedItemsDTO implements Serializable {
    private TableDTO table;
    private ItemListDTO itemList;

    public List<ItemDTO> getItems() {
        return itemList.getItems();
    }
}
