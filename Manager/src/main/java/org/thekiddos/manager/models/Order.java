package org.thekiddos.manager.models;

import lombok.Getter;

import javax.persistence.Table;
import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

//TODO This creates an order table with only an id field redundant fix it.
@Entity
@Getter
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @ElementCollection
    @CollectionTable(name = "ORDER_ITEMS",
            joinColumns = {@JoinColumn(name = "ORDER_ID", referencedColumnName = "ID")})
    @MapKeyJoinColumn(name = "ITEM_ID")
    @Column(name = "Quantity")
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
