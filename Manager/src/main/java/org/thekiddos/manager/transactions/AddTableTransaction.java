package org.thekiddos.manager.transactions;

import lombok.Setter;
import org.thekiddos.manager.models.Table;
import org.thekiddos.manager.repositories.Database;

public class AddTableTransaction implements Transaction {
    private Long tableId;
    private int maxCapacity;
    @Setter
    private double tableFee = 0.0;
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
        Table table = new Table( tableId, maxCapacity, tableFee );
        Database.addTable( table );
    }
}
