package org.thekiddos.manager;

import org.junit.jupiter.api.Test;
import org.thekiddos.manager.models.Invoice;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManagerTest {
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
        assertEquals( itemId, invoice.getItems().get( 0 ).getId() );
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
    // TODO group related items in a quantity field
    // TODO again Make sure we can't mutate anything outside of transactions(package)
}
