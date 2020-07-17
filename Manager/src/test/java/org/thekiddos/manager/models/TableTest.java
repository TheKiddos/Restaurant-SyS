package org.thekiddos.manager.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddCustomerTransaction;
import org.thekiddos.manager.transactions.AddTableTransaction;
import org.thekiddos.manager.transactions.DeleteTableTransaction;
import org.thekiddos.manager.transactions.ScheduledReservationTransaction;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class TableTest {

    @BeforeEach
    void setUpDatabase() {
        Database.deleteAll();
    }

    @Test
    void testAddTableTransaction() {
        Long tableId = 1L;
        AddTableTransaction addTable = new AddTableTransaction( tableId );
        addTable.setTableFee( 1.0 );
        addTable.execute();

        Table table = Database.getTableById( tableId );
        assertEquals( tableId, table.getId() );
        assertEquals( 4, table.getMaxCapacity() );
        assertEquals( 1.0, table.getFee() );
        assertFalse( table.isReserved( LocalDate.now() ) );
    }

    @Test
    void testAddTableWithSameId() {
        Long tableId = 1L;
        AddTableTransaction addTable = new AddTableTransaction( tableId );
        addTable.execute();

        assertThrows( IllegalArgumentException.class, () -> new AddTableTransaction( tableId ) );
    }

    @Test
    void testDeleteTableTransaction() {
        Long tableId = 2L;
        AddTableTransaction addTable = new AddTableTransaction( tableId );
        addTable.execute();

        Table table = Database.getTableById( tableId );
        assertNotNull( table );

        DeleteTableTransaction deleteTable = new DeleteTableTransaction( tableId );
        deleteTable.execute();

        table = Database.getTableById( tableId );
        assertNull( table );
    }

    @Test
    void testDeleteTableThatDoesNotExists() {
        assertThrows( IllegalArgumentException.class, () -> new DeleteTableTransaction( 1L ) );
    }

    @Test
    void testDeleteReservedTableTransaction() {
        Long customerId = 1L;
        new AddCustomerTransaction( customerId, "Kiddo", "mp4-12cs5@outlook.com", "12345678" ).execute();

        Long tableId = 1L;
        new AddTableTransaction( tableId ).execute();

        new ScheduledReservationTransaction( tableId, customerId, LocalDate.now(), LocalTime.now() ).execute();

        assertThrows( IllegalArgumentException.class, () -> new DeleteTableTransaction( tableId ) );
    }
}