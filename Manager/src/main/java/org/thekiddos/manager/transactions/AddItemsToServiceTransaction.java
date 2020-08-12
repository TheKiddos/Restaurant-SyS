package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This is used as a generic way of providing {@link org.thekiddos.manager.models.Item}
 * to the costumer (in tables, outside deliveries, ...etc)
 */
public abstract class AddItemsToServiceTransaction implements Transaction {
    private static final int QUANTITY_LIMIT_PER_ITEM = 1000;
    protected Service service;
    private final List<Long> items = new ArrayList<>();

    /**
     * Adds the items to the order
     */
    @Override
    public void execute() {
        for ( Long item : items )
            service.addItem( item );
        saveToDatabase();
    }

    /**
     * Adds an {@link org.thekiddos.manager.models.Item} to the reservation's {@link org.thekiddos.manager.models.Order}
     * @param itemId
     */
    public void addItem( Long itemId ) {
        if ( quantityDoesNotExceedsLimit( itemId ) )
            items.add( itemId );
    }

    private boolean quantityDoesNotExceedsLimit( Long itemId ) {
        int itemQuantity = 0;
        for ( Long item : items ) {
            if ( item.equals( itemId ) )
                ++itemQuantity;
        }

        return itemQuantity < QUANTITY_LIMIT_PER_ITEM;
    }

    /**
     * Updates the database
     */
    abstract void saveToDatabase();
}
