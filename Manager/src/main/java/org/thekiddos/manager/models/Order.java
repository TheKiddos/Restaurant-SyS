package org.thekiddos.manager.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        // TODO return as readonly?
        return items;
    }

    public double getTotal() {
        return items.stream().mapToDouble( Item::getPrice ).sum();
    }

    public void addItem( Item item ) {
        items.add( item );
    }
}
