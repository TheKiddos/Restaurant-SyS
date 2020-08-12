package org.thekiddos.manager.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class DeliveryTest {
    private final Long customerId = 1L, customerId2 = 2L, itemId = 1L, itemId2 = 2L, itemId3 = 3L;
    private final List<Long> items = Arrays.asList( 1L, 2L, 3L, 1L );

    @BeforeEach
    void setUp() {
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
        Transaction deliveryTransaction = new ImmediateDeliveryTransaction( customerId, "Hell", 1000, items );
        deliveryTransaction.execute();

        List<Delivery> deliveries = Database.getDeliveryByCustomerId( customerId );
        validateDelivery( deliveries, 1, 0, customerId, "Hell", 1012, LocalDate.now(), LocalTime.now() );
        validateOrder( deliveries.get( 0 ).getOrder(), 3, 12 );
    }

    @Test
    void testDeliveryWithNoCustomer() {
        assertThrows( IllegalArgumentException.class, () -> new ImmediateDeliveryTransaction( Util.INVALID_ID, "Hell", 1000, items ) );
    }

    // TODO I need to rethink the design between Service and Order Or I can't allow a customer to reserve more than one table a day (Maybe he can join tables)
    @Test
    void testCustomerRequestsTwoDeliveries() {
         new ImmediateDeliveryTransaction( customerId, "Hell", 1000, items ).execute();

        assertThrows( IllegalArgumentException.class, () -> new ImmediateDeliveryTransaction( customerId, "Hell", 1000, items ) );

        List<Delivery> deliveries = Database.getDeliveryByCustomerId( customerId );
        validateDelivery( deliveries, 1, 0, customerId, "Hell", 1012, LocalDate.now(), LocalTime.now() );
        validateOrder( deliveries.get( 0 ).getOrder(), 3, 12 );
    }

    @Test
    void testDeleteDelivery() {
        new ImmediateDeliveryTransaction( customerId, "Hell", 1000, items ).execute();

        List<Delivery> deliveries = Database.getDeliveryByCustomerId( customerId );
        validateDelivery( deliveries, 1, 0, customerId, "Hell", 1012, LocalDate.now(), LocalTime.now() );
        validateOrder( deliveries.get( 0 ).getOrder(), 3, 12 );

        DeleteDeliveryTransaction deleteDeliveryTransaction = new DeleteDeliveryTransaction( customerId, LocalDate.now() );
        deleteDeliveryTransaction.execute();

        deliveries = Database.getDeliveryByCustomerId( customerId );
        assertEquals( 0, deliveries.size() );
    }

    @Test
    void testDeleteFalseDelivery() {
        assertThrows( IllegalArgumentException.class, () -> new DeleteDeliveryTransaction( customerId, LocalDate.now() ) );
    }

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
        assertEquals( numberOfItems, order.getItemsQuantities().size() );
        assertEquals( total, order.getTotal() );
    }
}