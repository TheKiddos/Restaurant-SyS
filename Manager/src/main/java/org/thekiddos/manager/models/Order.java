package org.thekiddos.manager.models;

import lombok.Getter;

import javax.persistence.Table;
import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * An order holds the items that the customer requested, along side their total value and information
 */
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @Getter
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_items",
            joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "item_id")
    @Column(name = "quantity")
    private final Map<Item, Integer> items = new HashMap<>();

    /**
     * @return A Map that maps the items to their quantity
     */
    public Map<Item, Integer> getItems() {
        return Collections.unmodifiableMap( items );
    }

    /**
     * @return total worth of the ordered items
     */
    public double getTotal() {
        double total = 0;
        for ( Item item : items.keySet() )
            total += item.getPrice() * items.get( item );
        return total;
    }

    /**
     * @param item
     * @return true if the item is in the order, false otherwise
     */
    public boolean containsItem( Item item ) {
        Integer count = items.get( item );
        return count != null && count > 0;
    }

    /**
     * Adds an item to the order
     * @param item
     */
    public void addItem( Item item ) {
        Integer count = items.get( item );
        int QUANTITY_LIMIT_PER_ITEM = 1000;
        if ( count != null && count >= QUANTITY_LIMIT_PER_ITEM ) {
            return;
        }
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
