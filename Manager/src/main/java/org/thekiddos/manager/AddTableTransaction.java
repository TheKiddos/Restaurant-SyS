package org.thekiddos.manager;

import org.thekiddos.manager.models.Table;
import org.thekiddos.manager.repositories.Database;

public class AddTableTransaction implements Transaction {
    private Long tableId;
    private int maxCapacity;
    private static final int DEFAULT_MAX_CAPACITY = 4;

    public AddTableTransaction( Long tableId ) {
        this( tableId, DEFAULT_MAX_CAPACITY );
    }

    public AddTableTransaction( Long tableId, int maxCapacity ) {
        this.tableId = tableId;
        this.maxCapacity = maxCapacity;
    }

    @Override
    public void execute() {
        Table table = new Table( tableId, maxCapacity );
        Database.addTable( table );
    }
}
