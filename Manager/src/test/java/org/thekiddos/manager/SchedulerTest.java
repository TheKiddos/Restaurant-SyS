package org.thekiddos.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddCustomerTransaction;
import org.thekiddos.manager.transactions.AddTableTransaction;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class SchedulerTest {
    private final Long tableId = 1L, tableId2 = 2L, customerId = 1L, customerId2 = 2L;

    @BeforeEach
    void setUpDatabase() {
        Database.deleteAll();
        fillDatabase();
    }

    private void fillDatabase() {
        new AddTableTransaction( tableId ).execute();
        new AddTableTransaction( tableId2 ).execute();
        new AddCustomerTransaction( customerId, "Kiddo", "mp4-12cs5@outlook.com", "12345678" ).execute();
        new AddCustomerTransaction( customerId2, "Kiddo2", "mp412cs5@outlook.com", "12345678" ).execute();
    }

    @Test
    void testOverdueReservation() {
        LocalDateTime twentyMinutesOverdue = LocalDateTime.now().minusMinutes( 20 );
        // Skipping Transaction because it won't allow creating overdue reservations
        Reservation reservation = new Reservation( tableId, customerId, twentyMinutesOverdue.toLocalDate(), twentyMinutesOverdue.toLocalTime(), 0.0 );
        assertTrue( reservation.isOverdue() );

        Database.addReservation( reservation );
        // I really can't wait for 15 minutes in the tests
        Scheduler scheduler = Database.getBean( Scheduler.class );
        scheduler.handleOverdueReservations();
        assertEquals( 0, Database.getReservations().size() );
    }
}