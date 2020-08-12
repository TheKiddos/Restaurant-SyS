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
import java.time.Month;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class ReservationTest {
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
    void testScheduledReservationTransactionOnWrongDate() {
        LocalDate fifthOfNovember2019 = LocalDate.of( 2019, Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        assertThrows( IllegalArgumentException.class, () -> new ScheduledReservationTransaction( tableId, customerId, fifthOfNovember2019, eightPM ) );
        assertThrows( IllegalArgumentException.class, () -> new ScheduledReservationTransaction( tableId, customerId, null, eightPM ) );
        assertThrows( IllegalArgumentException.class, () -> new ScheduledReservationTransaction( tableId, customerId, fifthOfNovember2019, null ) );

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 0, tableReservations.size() );
    }

    @Test
    void testScheduledReservationTransaction() {
        LocalDate fifthOfNovemberNextYear = LocalDate.of( LocalDate.now().plusYears( 1 ).getYear(), Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        ScheduledReservationTransaction reserveTable = new ScheduledReservationTransaction( tableId, customerId, fifthOfNovemberNextYear, eightPM );

        assertEquals( 10.0, reserveTable.getReservationFee() );
        reserveTable.setReservationFee( 0.0 );
        assertEquals( 0.0, reserveTable.getReservationFee() );
        reserveTable.execute();

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, false, 0.0, fifthOfNovemberNextYear, eightPM );
    }

    @Test
    void testReserveTableThreeTimesADay() {
        LocalDate fifthOfNovemberNextYear = LocalDate.of( LocalDate.now().plusYears( 1 ).getYear(), Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        new ScheduledReservationTransaction( tableId, customerId, fifthOfNovemberNextYear, eightPM ).execute();

        LocalTime ninePM = LocalTime.of( 21, 0 );
        assertThrows( IllegalArgumentException.class, () -> new ScheduledReservationTransaction( tableId, customerId2, fifthOfNovemberNextYear, ninePM ) );

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, false, 10.0, fifthOfNovemberNextYear, eightPM );

        List<Reservation> customerReservations = Database.getReservationsByCustomerId( customerId2 );
        assertEquals( 0, customerReservations.size() );

        LocalTime sevenPM = LocalTime.of( 19, 0 );
        assertThrows( IllegalArgumentException.class, () -> new ScheduledReservationTransaction( tableId, customerId2, fifthOfNovemberNextYear, sevenPM ) );

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, false, 10.0, fifthOfNovemberNextYear, eightPM );

        customerReservations = Database.getReservationsByCustomerId( customerId2 );
        assertEquals( 0, customerReservations.size() );
    }

    @Test
    void testReserveTableOnTwoDays() {
        LocalDate fifthOfNovemberNextYear = LocalDate.of( LocalDate.now().plusYears( 1 ).getYear(), Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        Transaction reserveTable = new ScheduledReservationTransaction( tableId, customerId, fifthOfNovemberNextYear, eightPM );
        reserveTable.execute();

        LocalDate sixthOfNovemberNextYear = fifthOfNovemberNextYear.plusDays( 1 );

        reserveTable = new ScheduledReservationTransaction( tableId, customerId2, sixthOfNovemberNextYear, eightPM );
        reserveTable.execute();

        assertEquals( 2, Database.getReservationsByTableId( tableId ).size() );

        List<Reservation> customerReservations = Database.getReservationsByCustomerId( customerId );
        List<Reservation> customer2Reservations = Database.getReservationsByCustomerId( customerId2 );
        validateReservation( customerReservations, 1, 0, tableId, customerId, false, 10.0, fifthOfNovemberNextYear, eightPM );
        validateReservation( customer2Reservations, 1, 0, tableId, customerId2, false, 10.0, sixthOfNovemberNextYear, eightPM );
    }

    @Test
    void testImmediateReservationTransaction() {
        Transaction immediateReservation = new ImmediateReservationTransaction( tableId, customerId );
        immediateReservation.execute();

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );
    }

    @Test
    void testReserveWithNoCustomerOrTable() {
        assertThrows( IllegalArgumentException.class, () -> new ImmediateReservationTransaction( tableId, Util.INVALID_ID ) );
        assertThrows( IllegalArgumentException.class, () -> new ImmediateReservationTransaction( Util.INVALID_ID, customerId ) );
        assertThrows( IllegalArgumentException.class, () -> new ScheduledReservationTransaction( tableId, Util.INVALID_ID, LocalDate.now(), LocalTime.now() ) );
        assertThrows( IllegalArgumentException.class, () -> new ScheduledReservationTransaction( Util.INVALID_ID, customerId, LocalDate.now(), LocalTime.now() ) );
    }

    @Test
    void testImmediateReservationTransactionOnReservedTableSameDay() {
        new ImmediateReservationTransaction( tableId, customerId ).execute();

        assertThrows( IllegalArgumentException.class, () -> new ImmediateReservationTransaction( tableId, customerId2 ) );

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );

        List<Reservation> customer2Reservations = Database.getReservationsByCustomerId( customerId2 );
        assertEquals( 0, customer2Reservations.size() );
    }

    // TODO I need to rethink the design between Service and Order Or I can't allow a customer to reserve more than one table a day (Maybe he can join tables)
    @Test
    void testCustomerReservesTwoTablesSameDay() {
        new ImmediateReservationTransaction( tableId, customerId ).execute();
        assertThrows( IllegalArgumentException.class, () -> new ImmediateReservationTransaction( tableId2, customerId ) );

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );
        validateReservation( Database.getReservationsByCustomerId( customerId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );
    }

    @Test
    void testActivateReservationWrongDay() {
        LocalDate fifthOfNovemberNextYear = LocalDate.of( LocalDate.now().plusYears( 1 ).getYear(), Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        Transaction reserveTable = new ScheduledReservationTransaction( tableId, customerId, fifthOfNovemberNextYear, eightPM );
        reserveTable.execute();

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, false, 10.0, fifthOfNovemberNextYear, eightPM );

        assertThrows( IllegalArgumentException.class, () -> new ActivateReservationTransaction( tableId ) );
    }

    @Test
    void testActivateReservation() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        Transaction reserveTable = new ScheduledReservationTransaction( tableId, customerId, today, now );
        reserveTable.execute();

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, false, 10.0, today, now );

        new ActivateReservationTransaction( tableId ).execute();

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 10.0, today, now );
    }

    @Test
    void testDeleteReservation() {
        new ScheduledReservationTransaction( tableId, customerId, LocalDate.now(), LocalTime.now() ).execute();

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, false, 10.0, LocalDate.now(), null );

        DeleteReservationTransaction deleteReservation = new DeleteReservationTransaction( tableId, LocalDate.now() );
        deleteReservation.execute();

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 0, tableReservations.size() );
    }

    @Test
    void testDeleteActiveReservation() {
        new ImmediateReservationTransaction( tableId, customerId ).execute();

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );

        assertThrows( IllegalArgumentException.class, () -> new DeleteReservationTransaction( tableId, LocalDate.now() ) );

        validateReservation( Database.getReservationsByTableId( tableId ), 1, 0, tableId, customerId, true, 0.0, LocalDate.now(), null );
    }

    @Test
    void testDeleteFalseReservation() {
        assertThrows( IllegalArgumentException.class, () -> new DeleteReservationTransaction( tableId, LocalDate.now() ) );
    }

    private void validateReservation( List<Reservation> reservations, int expectedSize, int indexToCheck, Long tableId, Long customerId, boolean isActive, double total, LocalDate date, LocalTime time ) {
        assertEquals( expectedSize, reservations.size() );
        Reservation reservation = reservations.get( indexToCheck );
        assertNotNull( reservation );
        assertEquals( customerId, reservation.getCustomerId() );
        assertEquals( tableId, reservation.getReservedTableId() );
        assertEquals( total, reservation.getTotal() );
        assertEquals( isActive, reservation.isActive() );

        if ( date != null )
            assertEquals( date, reservation.getDate() );

        if ( time != null )
            assertEquals( 0, time.until( reservation.getTime(), MINUTES ) );
    }
}