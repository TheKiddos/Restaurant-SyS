package org.thekiddos.manager.transactions;

import org.thekiddos.manager.repositories.Database;

public class DeleteTableTransaction implements Transaction {
    private Long tableId;

    public DeleteTableTransaction( Long tableId ) {
        this.tableId = tableId;
    }

    @Override
    public void execute() {
        Database.removeTableById( tableId );
    }
}
