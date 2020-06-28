package org.thekiddos.manager.transactions;

import org.thekiddos.manager.repositories.Database;

public class DeleteItemTransaction implements Transaction {
    private final Long itemId;

    public DeleteItemTransaction( Long itemId ) {
        this.itemId = itemId;
    }

    @Override
    public void execute() {
        Database.removeItemById( itemId );
    }
}
