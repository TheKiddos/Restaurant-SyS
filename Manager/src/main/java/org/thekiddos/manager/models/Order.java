package org.thekiddos.manager.models;

import lombok.Getter;

import javax.persistence.Table;
import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//TODO This creates an order table with only an id field redundant fix it.
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @Getter
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ORDER_ITEMS",
            joinColumns = {@JoinColumn(name = "ORDER_ID", referencedColumnName = "ID")})
    @MapKeyJoinColumn(name = "ITEM_ID")
    @Column(name = "Quantity")
    private final Map<Item, Integer> items = new HashMap<>();

    public Map<Item, Integer> getItems() {
        return Collections.unmodifiableMap( items );
    }

    public double getTotal() {
        double total = 0;
        for ( Item item : items.keySet() )
            total += item.getPrice() * items.get( item );
        return total;
    }

    public boolean containsItem( Item item ) {
        Integer count = items.get( item );
        return count != null && count > 0;
    }

    public void addItem( Item item ) {
        Integer count = items.get( item );
        items.put( item, count == null ? 1 : count + 1 );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Order order = (Order) o;
        return id.equals( order.id );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }
}
