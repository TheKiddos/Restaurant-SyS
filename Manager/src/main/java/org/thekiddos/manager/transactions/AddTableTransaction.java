package org.thekiddos.manager.transactions;

import lombok.Setter;
import org.thekiddos.manager.models.SittingTable;
import org.thekiddos.manager.repositories.Database;

public class AddTableTransaction implements Transaction {
    private final Long tableId;
    private final int maxCapacity;
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
        SittingTable table = new SittingTable( tableId, maxCapacity, tableFee );
        Database.addTable( table );
    }
}
