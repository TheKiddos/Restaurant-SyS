package org.thekiddos.manager.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
public class InvoiceTest {
    private final Long tableId = 1L;
    private final Long customerId = 1L;
    private final Long itemId = 1L;

    @BeforeEach
    void setUpDatabase() {
        Database.deleteAll();
        fillDatabase();
    }

    private void fillDatabase() {
        new AddTableTransaction( tableId ).execute();
        new AddCustomerTransaction( customerId, "Kiddo", "mp4-12cs5@outlook.com", "12345678" ).execute();
        new AddItemTransaction( itemId, "French Fries", 10.0 ).execute();
    }

    @Test
    void testPayForOrderInReservation() {
        new ImmediateReservationTransaction( tableId, customerId ).execute();

        AddItemsToServiceTransaction service = new AddItemsToReservationTransaction( tableId );
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
    void testPayForOrderInDelivery() {
        new ImmediateDeliveryTransaction( customerId, "Hell", 1000, new ArrayList<>( Database.getItemsId() ) ).execute();

        List<Delivery> deliveries = Database.getDeliveryByCustomerId( customerId );
        assertEquals( 1, deliveries.size() );

        CheckOutTransaction checkOut = new CheckOutTransaction( deliveries.get( 0 ) );
        checkOut.execute();

        deliveries = Database.getDeliveryByCustomerId( customerId );
        assertEquals( 0, deliveries.size() );

        Invoice invoice = checkOut.getInvoice();
        assertNotNull( invoice );
        assertEquals( 1010.0, invoice.getTotal() );
        assertEquals( 10.0, invoice.getOrderTotal() );
        assertEquals( 0.0, invoice.getDiscount() );
        assertEquals( 1010.0, invoice.getNetAmount() );

        assertEquals( customerId, invoice.getCustomerId() );

        assertEquals( LocalDate.now(), invoice.getDate() );
        assertEquals( 1, invoice.getItems().size() );
        assertEquals( 1, invoice.getItems().get( Database.getItemById( itemId ) ) );
    }

    @Test
    void testPayForOrderWithInActiveReservation() {
        LocalDate fifthOfNovemberNextYear = LocalDate.of( LocalDate.now().plusYears( 1 ).getYear(), Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        new ScheduledReservationTransaction( tableId, customerId, fifthOfNovemberNextYear, eightPM ).execute();

        assertThrows( IllegalArgumentException.class, () -> new AddItemsToReservationTransaction( tableId ) );

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 1, tableReservations.size() );

        assertThrows( IllegalArgumentException.class, () -> new CheckOutTransaction( tableId ) );

        tableReservations = Database.getReservationsByTableId( tableId );
        assertEquals( 1, tableReservations.size() );
    }
}
