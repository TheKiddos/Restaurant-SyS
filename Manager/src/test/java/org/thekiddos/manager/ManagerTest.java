package org.thekiddos.manager;

import org.junit.jupiter.api.Test;
import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.models.Table;
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
        Transaction reserveTable = new ScheduledReservationTransaction( tableId, customerId, fifthOfNovember2019, eightPM );
        reserveTable.execute();
        // TODO really need to use exceptions

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
        assertEquals( LocalDate.of( 2020, Month.NOVEMBER, 5 ), reservation.getReservationDate() );
        assertEquals( LocalTime.of( 20, 0 ), reservation.getReservationTime() );
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
        assertEquals( LocalDate.of( 2020, Month.NOVEMBER, 5 ), reservation.getReservationDate() );
        assertEquals( LocalTime.of( 20, 0 ), reservation.getReservationTime() );

        List<Reservation> customerReservations = Database.getReservationsByCustomerId( customerId2 );
        assertEquals( 0, customerReservations.size() );

        LocalTime sevenPM = LocalTime.of( 19, 0 );
        reserveTable = new ScheduledReservationTransaction( tableId, customerId2, fifthOfNovember2020, sevenPM );
        reserveTable.execute();

        reservation = Database.getReservationsByTableId( tableId ).get( 0 );
        assertNotNull( reservation );
        assertEquals( LocalDate.of( 2020, Month.NOVEMBER, 5 ), reservation.getReservationDate() );
        assertEquals( LocalTime.of( 20, 0 ), reservation.getReservationTime() );

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
        assertEquals( LocalDate.of( 2020, Month.NOVEMBER, 5 ), reservation.getReservationDate() );
        assertEquals( LocalTime.of( 20, 0 ), reservation.getReservationTime() );
        assertEquals( customerId, reservation.getCustomerId() );

        reservation = tableReservations.get( 1 );
        assertEquals( LocalDate.of( 2020, Month.NOVEMBER, 6 ), reservation.getReservationDate() );
        assertEquals( LocalTime.of( 20, 0 ), reservation.getReservationTime() );
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

    // TODO No need for Reservation subclasses
    // TODO activating Scheduled reservations 
}
