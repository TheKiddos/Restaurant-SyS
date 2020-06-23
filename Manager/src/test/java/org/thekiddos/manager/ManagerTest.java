package org.thekiddos.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.thekiddos.manager.models.Invoice;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {

    @BeforeEach
    void setUpDatabase() {
        Database.init();
    }

    @Test
    void testPayForOrder() {
        Long tableId = 20L;
        new AddTableTransaction( tableId ).execute();

        Long customerId = 20L;
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();

        Long itemId = 20L;
        new AddItemTransaction( itemId, "French Fries", 10.0 ).execute();

        new ImmediateReservationTransaction( tableId, customerId ).execute();

        AddServiceTransaction service = new AddReservationServiceTransaction( tableId );
        service.addItem( itemId );
        service.execute();

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 1, tableReservations.size() );

        CheckOutTransaction checkOut = new CheckOutTransaction( tableId );
        checkOut.execute();

        tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 0, tableReservations.size() );

        Invoice invoice = checkOut.getInvoice();
        assertNotNull( invoice );
        assertEquals( 10.0, invoice.getTotal() );
        assertEquals( 10.0, invoice.getOrderTotal() );
        assertEquals( 0.0, invoice.getTableFee() );
        assertEquals( 0.0, invoice.getReservationFee() );
        assertEquals( 0.0, invoice.getDiscount() );
        assertEquals( 10.0, invoice.getNetAmount() );

        assertEquals( customerId, invoice.getCustomerId() );
        assertEquals( tableId, invoice.getTableId() );

        assertEquals( LocalDate.now(), invoice.getDate() );
        assertEquals( 1, invoice.getItems().size() );
        assertEquals( 1, invoice.getItems().get( Database.getItemById( itemId ) ) );
    }

    @Test
    void testPayForOrderWithInActiveReservation() {
        Long tableId = 20L;
        new AddTableTransaction( tableId ).execute();

        Long customerId = 20L;
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();

        Long itemId = 20L;
        new AddItemTransaction( itemId, "French Fries", 10.0 ).execute();

        LocalDate fifthOfNovember2020 = LocalDate.of( 2020, Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        new ScheduledReservationTransaction( tableId, customerId, fifthOfNovember2020, eightPM ).execute();

        assertThrows( IllegalArgumentException.class, () -> new AddReservationServiceTransaction( tableId ) );

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 1, tableReservations.size() );

        assertThrows( IllegalArgumentException.class, () -> new CheckOutTransaction( tableId ) );

        tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 1, tableReservations.size() );
    }

    // TODO use package protection for the setters
    // TODO maybe Types should be a class or a string for dynamic use
    // When deleting something move it to another table instead
    // can we replace Order with a list?
    // Database should be transparent to all classes
    // paying orders should delete/move the reservation
    // test two reservation and add orders to them and pay
    // remember to save the reservation object after retrieving from an actual database
    // save the invoice in the database
    // TODO again Make sure we can't mutate anything outside of transactions(package)
}
