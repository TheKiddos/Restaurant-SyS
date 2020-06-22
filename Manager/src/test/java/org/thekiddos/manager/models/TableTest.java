package org.thekiddos.manager.models;

import org.junit.jupiter.api.Test;
import org.thekiddos.manager.AddTableTransaction;
import org.thekiddos.manager.DeleteTableTransaction;
import org.thekiddos.manager.Transaction;
import org.thekiddos.manager.repositories.Database;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    @Test
    void testAddTableTransaction() {
        Long tableId = 1L;
        Transaction addTable = new AddTableTransaction( tableId );
        addTable.execute();

        Table table = Database.getTableById( tableId );
        assertNotNull( table );
        assertEquals( 4, table.getMaxCapacity() );
    }

    @Test
    void testDeleteTableTransaction() {
        Long tableId = 1L;
        Transaction addTable = new AddTableTransaction( tableId );
        addTable.execute();

        Table table = Database.getTableById( tableId );
        assertNotNull( table );

        Transaction deleteTable = new DeleteTableTransaction( tableId );
        deleteTable.execute();

        table = Database.getTableById( tableId );
        assertNull( table );
    }
}