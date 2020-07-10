package org.thekiddos.manager.transactions;

import lombok.Setter;
import org.thekiddos.manager.models.Table;
import org.thekiddos.manager.repositories.Database;

/**
 * This transaction is used to add a new {@link Table} (so the id must be unique)
 */
public class AddTableTransaction implements Transaction {
    private final Long tableId;
    private final int maxCapacity;
    @Setter
    private double tableFee = 0.0;
    private static final int DEFAULT_MAX_CAPACITY = 4;

    /**
     * @throws IllegalArgumentException if the tableId already exists
     */
    public AddTableTransaction( Long tableId ) {
        this( tableId, DEFAULT_MAX_CAPACITY );
    }

    /**
     * @throws IllegalArgumentException if the tableId already exists
     */
    public AddTableTransaction( Long tableId, int maxCapacity ) {
        if ( Database.getTableById( tableId ) != null )
            throw new IllegalArgumentException("A table with id " + tableId + " already exists" );
        this.tableId = tableId;
        this.maxCapacity = maxCapacity;
    }

    /**
     * Adds the table
     */
    @Override
    public void execute() {
        Table table = new Table( tableId, maxCapacity, tableFee );
        Database.addTable( table );
    }
}
