package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.repositories.Database;

/**
 * Deletes an {@link Item}, the Item must already exists and bot be in a current {@link org.thekiddos.manager.models.Order}
 */
public class DeleteItemTransaction implements Transaction {
    private final Item item;

    /**
     * @param itemId The Item id to be deleted
     * @throws IllegalArgumentException if the item does not exists or the item is in an order
     */
    public DeleteItemTransaction( Long itemId ) {
        item = Database.getItemById( itemId );

        if ( item == null )
            throw new IllegalArgumentException("No item with id " + itemId + " found" );

        if ( Database.isItemInAnyOrder( item ) )
            throw new IllegalArgumentException( "The Item is in an order and can't be deleted" );
    }

    /**
     * Deletes the item
     */
    @Override
    public void execute() {
        Database.removeItem( item );
    }
}
