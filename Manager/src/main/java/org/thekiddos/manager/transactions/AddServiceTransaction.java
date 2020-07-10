package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This is used as a generic way of providing {@link org.thekiddos.manager.models.Item}
 * to the costumer (in tables, outside deliveries, ...etc)
 */
public abstract class AddServiceTransaction implements Transaction {
    protected Service service;
    private final List<Long> items = new ArrayList<>();

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
        items.add( itemId );
    }

    abstract void saveToDatabase();
}
