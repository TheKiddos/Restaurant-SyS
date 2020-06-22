package org.thekiddos.manager.models;

import org.junit.jupiter.api.Test;
import org.thekiddos.manager.*;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void testAddOrderToReservation() {
        Long tableId = 15L;
        new AddTableTransaction( tableId ).execute();

        Long customerId = 15L;
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();

        Long itemId = 15L;
        new AddItemTransaction( itemId, "French Fries", 10.0 ).execute();

        new ImmediateReservationTransaction( tableId, customerId ).execute();

        AddServiceTransaction service = new AddReservationServiceTransaction( tableId );
        service.addItem( itemId );
        service.execute();

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 1, tableReservations.size() );

        Reservation reservation = tableReservations.get( 0 );
        assertNotNull( reservation );
        assertEquals( customerId, reservation.getCustomerId() );
        assertEquals( 10.0, reservation.getTotal() );

        Order order = reservation.getOrder();
        assertEquals( 1, order.getItems().size() );
        assertEquals( itemId, order.getItems().get( 0 ).getId() );
        assertEquals( 10.0, order.getTotal() );
    }

    @Test
    void testAddOrderToNonActiveReservationInFuture() {
        Long tableId = 16L;
        new AddTableTransaction( tableId ).execute();

        Long customerId = 16L;
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();

        Long itemId = 16L;
        new AddItemTransaction( itemId, "French Fries", 10.0 ).execute();

        LocalDate fifthOfNovember2020 = LocalDate.of( 2020, Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        new ScheduledReservationTransaction( tableId, customerId, fifthOfNovember2020, eightPM ).execute();

        assertThrows( IllegalArgumentException.class, () -> new AddReservationServiceTransaction( tableId ) );

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 1, tableReservations.size() );

        Reservation reservation = tableReservations.get( 0 );
        assertNotNull( reservation );
        assertFalse( reservation.isActive() );
        assertEquals( customerId, reservation.getCustomerId() );

        Order order = reservation.getOrder();
        assertEquals( 0, order.getItems().size() );
        assertEquals( 0.0, order.getTotal() );
    }

    @Test
    void testAddOrderToNonActiveReservationToday() {
        Long tableId = 17L;
        new AddTableTransaction( tableId ).execute();

        Long customerId = 17L;
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();

        Long itemId = 17L;
        new AddItemTransaction( itemId, "French Fries", 10.0 ).execute();

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        new ScheduledReservationTransaction( tableId, customerId, currentDate, currentTime ).execute();

        assertThrows( IllegalArgumentException.class, () -> new AddReservationServiceTransaction( tableId ) );

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 1, tableReservations.size() );

        Reservation reservation = tableReservations.get( 0 );
        assertNotNull( reservation );
        assertFalse( reservation.isActive() );
        assertEquals( customerId, reservation.getCustomerId() );

        Order order = reservation.getOrder();
        assertEquals( 0, order.getItems().size() );
        assertEquals( 0.0, order.getTotal() );
    }

    @Test
    void testAddOrderToActiveReservationToday() {
        Long tableId = 18L;
        new AddTableTransaction( tableId ).execute();

        Long customerId = 18L;
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();

        Long itemId = 18L;
        new AddItemTransaction( itemId, "French Fries", 10.0 ).execute();

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        new ScheduledReservationTransaction( tableId, customerId, currentDate, currentTime ).execute();

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 1, tableReservations.size() );

        Reservation reservation = tableReservations.get( 0 );
        assertNotNull( reservation );

        Transaction activateReservation = new ActivateReservationTransaction( tableId ); // TODO Maybe we should rely on the customer Id
        activateReservation.execute();

        tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 1, tableReservations.size() );

        reservation = tableReservations.get( 0 );
        assertNotNull( reservation );

        AddServiceTransaction service = new AddReservationServiceTransaction( tableId );
        service.addItem( itemId );
        service.execute();

        assertTrue( reservation.isActive() );
        assertEquals( customerId, reservation.getCustomerId() );
        assertEquals( 20.0, reservation.getTotal() );

        Order order = reservation.getOrder();
        assertEquals( 1, order.getItems().size() );
        assertEquals( 10.0, order.getTotal() );
    }

    @Test
    void testAddTwoOrderToReservation() {
        Long tableId = 19L;
        new AddTableTransaction( tableId ).execute();

        Long customerId = 19L;
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();

        Long itemId = 19L;
        new AddItemTransaction( itemId, "French Fries", 10.0 ).execute();

        new ImmediateReservationTransaction( tableId, customerId ).execute();

        AddServiceTransaction service = new AddReservationServiceTransaction( tableId );
        service.addItem( itemId );
        service.execute();

        service = new AddReservationServiceTransaction( tableId );
        service.addItem( itemId );
        service.execute();

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 1, tableReservations.size() );

        Reservation reservation = tableReservations.get( 0 );
        assertNotNull( reservation );
        assertEquals( customerId, reservation.getCustomerId() );
        assertEquals( 20.0, reservation.getTotal() );

        Order order = reservation.getOrder();
        assertEquals( 2, order.getItems().size() );
        assertEquals( itemId, order.getItems().get( 0 ).getId() );
        assertEquals( itemId, order.getItems().get( 1 ).getId() );
        assertEquals( 20.0, order.getTotal() );
    }
}