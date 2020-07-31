package org.thekiddos.manager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class OrderedItem {
    private final String itemName;
    private final int quantity;
    private final double unitPrice;
}
