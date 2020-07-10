package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Table;
import org.thekiddos.manager.repositories.Database;

/**
 * Deletes a table that does not have any reservations
 */
public class DeleteTableTransaction implements Transaction {
    private final Table table;

    /**
     * @throws IllegalArgumentException if the table does not exists or if the table has any reservation
     */
    public DeleteTableTransaction( Long tableId ) {
        table = Database.getTableById( tableId );

        if ( table == null )
            throw new IllegalArgumentException( "No table with id " + tableId + " was found" );

        if ( table.hasReservation() )
            throw new IllegalArgumentException( "The table has reservation(s) and can't be deleted" );
    }

    /**
     * Deletes the table
     */
    @Override
    public void execute() {
        Database.removeTable( table );
    }
}
