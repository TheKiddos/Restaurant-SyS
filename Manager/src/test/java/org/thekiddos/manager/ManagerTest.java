package org.thekiddos.manager;

import org.junit.jupiter.api.Test;
import org.thekiddos.manager.models.*;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {
    @Test
    void testAddCustomerTransaction() {
        Long customerId = 1L;
        Transaction addCustomer = new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" );
        addCustomer.execute();

        Customer customer = Database.getCustomerById( customerId );
        assertNotNull( customer );
        assertEquals( "Kiddo", customer.getFirstName() );
        assertEquals( "Zahlt", customer.getLastName() );
    }

    @Test
    void testDeleteCustomerTransaction() {
        Long customerId = 1L;
        Transaction addCustomer = new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" );
        addCustomer.execute();

        Customer customer = Database.getCustomerById( customerId );
        assertNotNull( customer );

        Transaction deleteCustomer = new DeleteCustomerTransaction( customerId );
        deleteCustomer.execute();

        customer = Database.getCustomerById( customerId );
        assertNull( customer );
    }

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

    @Test
    void testScheduledReservationTransactionOnWrongDate() {
        Long tableId = 1L;
        Transaction addTable = new AddTableTransaction( tableId );
        addTable.execute();

        Long customerId = 1L;
        Transaction addCustomer = new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" );
        addCustomer.execute();

        LocalDate fifthOfNovember2019 = LocalDate.of( 2019, Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        assertThrows( IllegalArgumentException.class, () -> new ScheduledReservationTransaction( tableId, customerId, fifthOfNovember2019, eightPM ) );

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 0, tableReservations.size() );
    }

    @Test
    void testScheduledReservationTransaction() {
        Long tableId = 2L;
        Transaction addTable = new AddTableTransaction( tableId );
        addTable.execute();

        Long customerId = 2L;
        Transaction addCustomer = new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" );
        addCustomer.execute();

        LocalDate fifthOfNovember2020 = LocalDate.of( 2020, Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        Transaction reserveTable = new ScheduledReservationTransaction( tableId, customerId, fifthOfNovember2020, eightPM );
        reserveTable.execute();

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertNotEquals( 0, tableReservations.size() );
        Reservation reservation = tableReservations.get( 0 );
        assertNotNull( reservation );
        assertEquals( LocalDate.of( 2020, Month.NOVEMBER, 5 ), reservation.getDate() );
        assertEquals( LocalTime.of( 20, 0 ), reservation.getTime() );
    }

    @Test
    void testReserveTableTwiceADay() {
        Long tableId = 3L;
        Transaction addTable = new AddTableTransaction( tableId );
        addTable.execute();

        Long customerId = 3L;
        Transaction addCustomer = new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" );
        addCustomer.execute();

        LocalDate fifthOfNovember2020 = LocalDate.of( 2020, Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        Transaction reserveTable = new ScheduledReservationTransaction( tableId, customerId, fifthOfNovember2020, eightPM );
        reserveTable.execute();

        Long customerId2 = 4L;
        addCustomer = new AddCustomerTransaction( customerId2, "Kiddo", "Zahlt" );
        addCustomer.execute();

        LocalTime ninePM = LocalTime.of( 21, 0 );
        reserveTable = new ScheduledReservationTransaction( tableId, customerId2, fifthOfNovember2020, ninePM );
        reserveTable.execute();

        Reservation reservation = Database.getReservationsByTableId( tableId ).get( 0 );
        assertNotNull( reservation );
        assertEquals( LocalDate.of( 2020, Month.NOVEMBER, 5 ), reservation.getDate() );
        assertEquals( LocalTime.of( 20, 0 ), reservation.getTime() );

        List<Reservation> customerReservations = Database.getReservationsByCustomerId( customerId2 );
        assertEquals( 0, customerReservations.size() );

        LocalTime sevenPM = LocalTime.of( 19, 0 );
        reserveTable = new ScheduledReservationTransaction( tableId, customerId2, fifthOfNovember2020, sevenPM );
        reserveTable.execute();

        reservation = Database.getReservationsByTableId( tableId ).get( 0 );
        assertNotNull( reservation );
        assertEquals( LocalDate.of( 2020, Month.NOVEMBER, 5 ), reservation.getDate() );
        assertEquals( LocalTime.of( 20, 0 ), reservation.getTime() );

        customerReservations = Database.getReservationsByCustomerId( customerId2 );
        assertEquals( 0, customerReservations.size() );
    }

    @Test
    void testReserveTableOnTwoDays() {
        Long tableId = 5L;
        Transaction addTable = new AddTableTransaction( tableId );
        addTable.execute();

        Long customerId = 5L;
        Transaction addCustomer = new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" );
        addCustomer.execute();

        Long customerId2 = 6L;
        addCustomer = new AddCustomerTransaction( customerId2, "Kiddo", "Django" );
        addCustomer.execute();

        LocalDate fifthOfNovember2020 = LocalDate.of( 2020, Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );

        Transaction reserveTable = new ScheduledReservationTransaction( tableId, customerId, fifthOfNovember2020, eightPM );
        reserveTable.execute();

        LocalDate sixthOfNovember2020 = LocalDate.of( 2020, Month.NOVEMBER, 6 );

        reserveTable = new ScheduledReservationTransaction( tableId, customerId2, sixthOfNovember2020, eightPM );
        reserveTable.execute();

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertNotEquals( 0, tableReservations.size() );
        assertEquals( 2, tableReservations.size() );

        Reservation reservation = tableReservations.get( 0 );
        assertEquals( LocalDate.of( 2020, Month.NOVEMBER, 5 ), reservation.getDate() );
        assertEquals( LocalTime.of( 20, 0 ), reservation.getTime() );
        assertEquals( customerId, reservation.getCustomerId() );

        reservation = tableReservations.get( 1 );
        assertEquals( LocalDate.of( 2020, Month.NOVEMBER, 6 ), reservation.getDate() );
        assertEquals( LocalTime.of( 20, 0 ), reservation.getTime() );
        assertEquals( customerId2, reservation.getCustomerId() );
    }

    @Test
    void testImmediateReservationTransaction() {
        Long tableId = 7L;
        new AddTableTransaction( tableId ).execute();

        Long customerId = 7L;
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();

        Transaction immediateReservation = new ImmediateReservationTransaction( tableId, customerId );
        immediateReservation.execute();

        Reservation reservation = Database.getReservationsByTableId( tableId ).get( 0 );
        assertNotNull( reservation );
        assertEquals( customerId, reservation.getCustomerId() );
    }

    @Test
    void testImmediateReservationTransactionOnReservedTableSameDay() {
        Long tableId = 8L;
        new AddTableTransaction( tableId ).execute();

        Long customerId = 8L;
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();

        new ImmediateReservationTransaction( tableId, customerId ).execute();

        Reservation reservation = Database.getReservationsByTableId( tableId ).get( 0 );
        assertNotNull( reservation );
        assertEquals( customerId, reservation.getCustomerId() );

        Long customerId2 = 9L;
        new AddCustomerTransaction( customerId2, "Kiddo", "Django" ).execute();

        new ImmediateReservationTransaction( tableId, customerId ).execute();

        reservation = Database.getReservationsByTableId( tableId ).get( 0 );
        assertNotNull( reservation );
        assertEquals( customerId, reservation.getCustomerId() );

        List<Reservation> customer2Reservations = Database.getReservationsByCustomerId( customerId2 );
        assertEquals( 0, customer2Reservations.size() );
    }

    @Test
    void testCustomerReservesTwoTablesSameDay() {
        Long tableId = 10L;
        Long tableId2 = 11L;
        new AddTableTransaction( tableId ).execute();
        new AddTableTransaction( tableId2 ).execute();

        Long customerId = 10L;
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();

        new ImmediateReservationTransaction( tableId, customerId ).execute();
        new ImmediateReservationTransaction( tableId2, customerId ).execute();

        List<Reservation> customerReservations = Database.getReservationsByCustomerId( customerId );
        assertNotEquals( 0, customerReservations.size() );
        assertEquals( 2, customerReservations.size() );

        Set<Long> tableIdsExpected = Set.of( tableId, tableId2 );
        Set<Long> tableIdsActual = Set.of( customerReservations.get( 0 ).getTableId(), customerReservations.get( 1 ).getTableId() );
        assertEquals( tableIdsExpected, tableIdsActual );
    }

    @Test
    void testDeleteReservation() {
        Long tableId = 12L;
        new AddTableTransaction( tableId ).execute();

        Long customerId = 12L;
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();

        new ImmediateReservationTransaction( tableId, customerId ).execute();

        Reservation reservation = Database.getReservationsByTableId( tableId ).get( 0 );
        assertNotNull( reservation );

        DeleteReservationTransaction deleteReservation = new DeleteReservationTransaction( tableId, LocalDate.now() );
        assertEquals( customerId, deleteReservation.getCustomerId() );
        deleteReservation.execute();
        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 0, tableReservations.size() );
    }

    @Test
    void testDeleteFalseReservation() {
        Long tableId = 13L;
        new AddTableTransaction( tableId ).execute();

        Long customerId = 13L;
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();

        DeleteReservationTransaction deleteReservation = new DeleteReservationTransaction( tableId, LocalDate.now() );
        deleteReservation.execute();
        assertEquals( -1, deleteReservation.getCustomerId() );

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 0, tableReservations.size() );
    }

    @Test
    void testAddItemTransaction() {
        Long itemId = 14L;
        AddItemTransaction addItem = new AddItemTransaction( itemId, "French Fries", 10.0 );
        addItem.withDescription( "Well it's French Fries what else to say!" )
                .withType( Type.FOOD )
                .withType( Type.STARTER )
                .withType( Type.HOT )
                .withType( Type.SNACK )
                .withCalories( 10000.0 )
                .withFat( 51.0 )
                .withProtein( 0.4 )
                .withCarbohydrates( 0.2 );
        addItem.execute();

        Item frenchFries = Database.getItemById( itemId );
        assertNotNull( frenchFries );
        assertEquals( "French Fries", frenchFries.getName() );
        assertEquals( 10.0, frenchFries.getPrice() );
        assertEquals( 10000.0, frenchFries.getCalories() );
        assertEquals( 51.0, frenchFries.getFat() );
        assertEquals( 0.4, frenchFries.getProtein() );
        assertEquals( 0.2, frenchFries.getCarbohydrates() );
        Set<Type> types = Set.of( Type.FOOD, Type.STARTER, Type.HOT, Type.SNACK );
        assertEquals( types, frenchFries.getTypes() );
    }

    @Test
    void testDeleteItemTransaction() {
        Long itemId = 14L;
        new AddItemTransaction( itemId, "French Fries", 10.0 ).execute();

        Transaction deleteItem = new DeleteItemTransaction( itemId );
        deleteItem.execute();

        Item frenchFries = Database.getItemById( itemId );
        assertNull( frenchFries );
    }

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

        Transaction activateReservation = new ActivateReservationTransaction( reservation );
        activateReservation.execute();

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

    // TODO use package protection for the setters
    // TODO maybe Types should be a class or a string for dynamic use
    // As soon as an order is marked as payed it's removed to another table/database
    // can we replace Order with a list?
    // Database should be transparent to all classes
    // test adding orders later
    // paying orders this should delete/move the reservation
    // test two reservation and add orders to them and pay
    // remember to save the reservation object after retrieving from an actual database
}
