package org.thekiddos.manager.models;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private Map<Item, Integer> items = new HashMap<>();

    public Map<Item, Integer> getItems() {
        return items;
    }

    public double getTotal() {
        double total = 0;
        for ( Item item : items.keySet() )
            total += item.getPrice() * items.get( item );
        return total;
    }

    public void addItem( Item item ) {
        Integer count = items.get( item );
        items.put( item, count == null ? 1 : count + 1 );
    }
}
