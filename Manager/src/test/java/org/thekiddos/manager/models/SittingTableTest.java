package org.thekiddos.manager.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddTableTransaction;
import org.thekiddos.manager.transactions.DeleteTableTransaction;
import org.thekiddos.manager.transactions.Transaction;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SittingTableTest {

    @BeforeEach
    void setUpDatabase() {
        Database.init();
    }

    @Test
    void testAddTableTransaction() {
        Long tableId = 1L;
        AddTableTransaction addTable = new AddTableTransaction( tableId );
        addTable.setTableFee( 1.0 );
        addTable.execute();

        SittingTable table = Database.getTableById( tableId );
        assertNotNull( table );
        assertEquals( tableId, table.getId() );
        assertEquals( 4, table.getMaxCapacity() );
        assertEquals( 1.0, table.getTableFee() );
        assertFalse( table.isReserved( LocalDate.now() ) );
    }

    @Test
    void testDeleteTableTransaction() {
        Long tableId = 1L;
        Transaction addTable = new AddTableTransaction( tableId );
        addTable.execute();

        SittingTable table = Database.getTableById( tableId );
        assertNotNull( table );

        Transaction deleteTable = new DeleteTableTransaction( tableId );
        deleteTable.execute();

        table = Database.getTableById( tableId );
        assertNull( table );
    }

    // TODO test Delete Reserved Table NOW
    @Test
    void testDeleteReservedTableTransaction() {

    }
}