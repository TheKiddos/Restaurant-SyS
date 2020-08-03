package org.thekiddos.manager.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddCustomerTransaction;
import org.thekiddos.manager.transactions.AddItemTransaction;
import org.thekiddos.manager.transactions.ImmediateDeliveryTransaction;
import org.thekiddos.manager.transactions.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class DeliveryTest {
    private final Long customerId = 1L, customerId2 = 2L, itemId = 1L, itemId2 = 2L, itemId3 = 3L;

    @BeforeEach
    void setUpDatabase() {
        Database.deleteAll();
        fillDatabase();
    }

    private void fillDatabase() {
        new AddCustomerTransaction( customerId, "Kiddo", "mp4-12cs5@outlook.com", "12345678" ).execute();
        new AddCustomerTransaction( customerId2, "Kiddo2", "mp412cs5@outlook.com", "12345678" ).execute();
        new AddItemTransaction( itemId, "French Fries", 2.0 ).execute();
        new AddItemTransaction( itemId2, "Syrian Fries", 3.0 ).execute();
        new AddItemTransaction( itemId3, "Artificial Fries", 5.0 ).execute();
    }

    @Test
    void testImmediateDeliveryTransaction() {
        Set<Item> items = Database.getItems();
        Transaction deliveryTransaction = new ImmediateDeliveryTransaction( customerId, "Hell", 1000, items );
        deliveryTransaction.execute();

        List<Delivery> deliveries = Database.getDeliveryByCustomerId( customerId );
        validateDelivery( deliveries, 1, 0, customerId, "Hell", 1010, LocalDate.now(), LocalTime.now() );
        validateOrder( deliveries.get( 0 ).getOrder(), 3, 10 );
    }
/*
    @Test
    void testDeliverWithNoCustomer() {
        assertThrows( IllegalArgumentException.class, () -> new ImmediateReservationTransaction( tableId, -1L ) );
        assertThrows( IllegalArgumentException.class, () -> new ImmediateReservationTransaction( -1L, customerId ) );
        assertThrows( IllegalArgumentException.class, () -> new ScheduledReservationTransaction( -1L, customerId, LocalDate.now(), LocalTime.now() ) );
        assertThrows( IllegalArgumentException.class, () -> new ScheduledReservationTransaction( -1L, customerId, LocalDate.now(), LocalTime.now() ) );
    }

    @Test
    void testImmediateReservationTransactionOnReservedTableSameDay() {
        new ImmediateReservationTransaction( tableId, customerId ).execute();

        assertThrows( IllegalArgumentException.class, () -> new ImmediateReservationTransaction( tableId, customerId2 ) );

        validateDelivery( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );

        List<Reservation> customer2Reservations = Database.getReservationsByCustomerId( customerId2 );
        assertEquals( 0, customer2Reservations.size() );
    }

    // TODO I need to rethink the design between Service and Order Or I can't allow a customer to reserve more than one table a day (Maybe he can join tables)
    @Test
    void testCustomerReservesTwoTablesSameDay() {
        new ImmediateReservationTransaction( tableId, customerId ).execute();
        assertThrows( IllegalArgumentException.class, () -> new ImmediateReservationTransaction( tableId2, customerId ) );

        validateDelivery( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );
        validateDelivery( Database.getReservationsByCustomerId( customerId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );
    }

    @Test
    void testActivateReservationWrongDay() {
        LocalDate fifthOfNovemberNextYear = LocalDate.of( LocalDate.now().plusYears( 1 ).getYear(), Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        Transaction reserveTable = new ScheduledReservationTransaction( tableId, customerId, fifthOfNovemberNextYear, eightPM );
        reserveTable.execute();

        validateDelivery( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, false, 10.0, fifthOfNovemberNextYear, eightPM );

        assertThrows( IllegalArgumentException.class, () -> new ActivateReservationTransaction( tableId ) );
    }

    @Test
    void testActivateReservation() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        Transaction reserveTable = new ScheduledReservationTransaction( tableId, customerId, today, now );
        reserveTable.execute();

        validateDelivery( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, false, 10.0, today, now );

        new ActivateReservationTransaction( tableId ).execute();

        validateDelivery( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 10.0, today, now );
    }

    @Test
    void testDeleteReservation() {
        new ScheduledReservationTransaction( tableId, customerId, LocalDate.now(), LocalTime.now() ).execute();

        validateDelivery( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, false, 10.0, LocalDate.now(), null );

        DeleteReservationTransaction deleteReservation = new DeleteReservationTransaction( tableId, LocalDate.now() );
        deleteReservation.execute();

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 0, tableReservations.size() );
    }

    @Test
    void testDeleteActiveReservation() {
        new ImmediateReservationTransaction( tableId, customerId ).execute();

        validateDelivery( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );

        assertThrows( IllegalArgumentException.class, () -> new DeleteReservationTransaction( tableId, LocalDate.now() ) );

        validateDelivery( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );
    }

    @Test
    void testDeleteFalseReservation() {
        assertThrows( IllegalArgumentException.class, () -> new DeleteReservationTransaction( tableId, LocalDate.now() ) );
    }*/

    private void validateDelivery( List<Delivery> deliveries, int expectedSize, int indexToCheck, Long customerId, String address, double total, LocalDate date, LocalTime time ) {
        assertEquals( expectedSize, deliveries.size() );
        Delivery delivery = deliveries.get( indexToCheck );
        assertNotNull( delivery );
        assertEquals( customerId, delivery.getCustomerId() );
        assertEquals( total, delivery.getTotal() );
        assertEquals( address, delivery.getDeliveryAddress() );

        if ( date != null )
            assertEquals( date, delivery.getDate() );

        if ( time != null )
            assertEquals( 0, time.until( delivery.getTime(), MINUTES ) );
    }

    void validateOrder( Order order, int numberOfItems, double total ) {
        assertEquals( numberOfItems, order.getItems().size() );
        assertEquals( total, order.getTotal() );
    }
}