package org.thekiddos.manager.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.thekiddos.manager.*;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    private Long tableId = 1L, tableId2 = 2L, customerId = 1L, customerId2 = 2L;

    @BeforeEach
    void setUpDatabase() {
        Database.init();
        fillDatabase();
    }

    private void fillDatabase() {
        new AddTableTransaction( tableId ).execute();
        new AddTableTransaction( tableId2 ).execute();
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();
        new AddCustomerTransaction( customerId2, "Kiddo", "Django" ).execute();
    }

    @Test
    void testScheduledReservationTransactionOnWrongDate() {
        LocalDate fifthOfNovember2019 = LocalDate.of( 2019, Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        assertThrows( IllegalArgumentException.class, () -> new ScheduledReservationTransaction( tableId, customerId, fifthOfNovember2019, eightPM ) );

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 0, tableReservations.size() );
    }

    @Test
    void testScheduledReservationTransaction() {
        LocalDate fifthOfNovember2020 = LocalDate.of( 2020, Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        ScheduledReservationTransaction reserveTable = new ScheduledReservationTransaction( tableId, customerId, fifthOfNovember2020, eightPM );

        assertEquals( 10.0, reserveTable.getReservationFee() );
        reserveTable.setReservationFee( 0.0 );
        assertEquals( 0.0, reserveTable.getReservationFee() );
        reserveTable.execute();

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, false, 0.0, fifthOfNovember2020, eightPM );
    }

    @Test
    void testReserveTableThreeTimesADay() {
        LocalDate fifthOfNovember2020 = LocalDate.of( 2020, Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        new ScheduledReservationTransaction( tableId, customerId, fifthOfNovember2020, eightPM ).execute();

        LocalTime ninePM = LocalTime.of( 21, 0 );
        assertThrows( IllegalArgumentException.class, () -> new ScheduledReservationTransaction( tableId, customerId2, fifthOfNovember2020, ninePM ) );

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, false, 10.0, fifthOfNovember2020, eightPM );

        List<Reservation> customerReservations = Database.getReservationsByCustomerId( customerId2 );
        assertEquals( 0, customerReservations.size() );

        LocalTime sevenPM = LocalTime.of( 19, 0 );
        assertThrows( IllegalArgumentException.class, () -> new ScheduledReservationTransaction( tableId, customerId2, fifthOfNovember2020, sevenPM ) );

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, false, 10.0, fifthOfNovember2020, eightPM );

        customerReservations = Database.getReservationsByCustomerId( customerId2 );
        assertEquals( 0, customerReservations.size() );
    }

    @Test
    void testReserveTableOnTwoDays() {
        LocalDate fifthOfNovember2020 = LocalDate.of( 2020, Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        Transaction reserveTable = new ScheduledReservationTransaction( tableId, customerId, fifthOfNovember2020, eightPM );
        reserveTable.execute();

        LocalDate sixthOfNovember2020 = LocalDate.of( 2020, Month.NOVEMBER, 6 );

        reserveTable = new ScheduledReservationTransaction( tableId, customerId2, sixthOfNovember2020, eightPM );
        reserveTable.execute();

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        validateReservation( tableReservations, 2, 0, tableId, customerId, false, 10.0, fifthOfNovember2020, eightPM );
        validateReservation( tableReservations, 2, 1, tableId, customerId2, false, 10.0, sixthOfNovember2020, eightPM );
    }

    @Test
    void testImmediateReservationTransaction() {
        Transaction immediateReservation = new ImmediateReservationTransaction( tableId, customerId );
        immediateReservation.execute();

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );
    }

    @Test
    void testImmediateReservationTransactionOnReservedTableSameDay() {
        new ImmediateReservationTransaction( tableId, customerId ).execute();

        assertThrows( IllegalArgumentException.class, () -> new ImmediateReservationTransaction( tableId, customerId2 ) );

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );

        List<Reservation> customer2Reservations = Database.getReservationsByCustomerId( customerId2 );
        assertEquals( 0, customer2Reservations.size() );
    }

    @Test
    void testCustomerReservesTwoTablesSameDay() {
        new ImmediateReservationTransaction( tableId, customerId ).execute();
        new ImmediateReservationTransaction( tableId2, customerId ).execute();

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );
        validateReservation( Database.getReservationsByTableId( tableId2 ), 1, 0, tableId2, customerId, true, 0.0, LocalDate.now(), null );
        validateReservation( Database.getReservationsByCustomerId( customerId ), 2, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );
        validateReservation( Database.getReservationsByCustomerId( customerId ), 2, 1, tableId2, customerId, true, 0.0, LocalDate.now(), null );
    }

    @Test
    void testActivateReservationWrongDay() {
        LocalDate fifthOfNovember2020 = LocalDate.of( 2020, Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        Transaction reserveTable = new ScheduledReservationTransaction( tableId, customerId, fifthOfNovember2020, eightPM );
        reserveTable.execute();

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, false, 10.0, fifthOfNovember2020, eightPM );

        assertThrows( IllegalArgumentException.class, () -> new ActivateReservationTransaction( tableId ) );
    }

    @Test
    void testDeleteReservation() {
        new ImmediateReservationTransaction( tableId, customerId ).execute();

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );

        DeleteReservationTransaction deleteReservation = new DeleteReservationTransaction( tableId, LocalDate.now() );
        assertEquals( customerId, deleteReservation.getCustomerId() );
        deleteReservation.execute();

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 0, tableReservations.size() );
    }

    @Test
    void testDeleteFalseReservation() {
        DeleteReservationTransaction deleteReservation = new DeleteReservationTransaction( tableId, LocalDate.now() );
        deleteReservation.execute();
        assertEquals( -1, deleteReservation.getCustomerId() );

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 0, tableReservations.size() );
    }

    private void validateReservation( List<Reservation> reservations, int expectedSize, int indexToCheck, Long tableId, Long customerId, boolean isActive, double total, LocalDate date, LocalTime time ) {
        assertEquals( expectedSize, reservations.size() );
        Reservation reservation = reservations.get( indexToCheck );
        assertNotNull( reservation );
        assertEquals( customerId, reservation.getCustomerId() );
        assertEquals( tableId, reservation.getTableId() );
        assertEquals( total, reservation.getTotal() );
        assertEquals( isActive, reservation.isActive() );

        if ( date != null )
            assertEquals( date, reservation.getDate() );

        if ( time != null )
            assertEquals( time, reservation.getTime() );
    }
}