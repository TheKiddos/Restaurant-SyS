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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class CustomerTest {

    @BeforeEach
    void setUpDatabase() {
        Database.deleteAll();
    }

    @Test
    void testAddCustomerTransaction() {
        Long customerId = 1L;
        Transaction addCustomer = new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" );
        addCustomer.execute();

        Customer customer = Database.getCustomerById( customerId );
        assertNotNull( customer );
        assertEquals( customerId, customer.getId() );
        assertEquals( "Kiddo", customer.getFirstName() );
        assertEquals( "Zahlt", customer.getLastName() );
    }

    @Test
    void testAddCustomerWithSameId() {
        Long customerId = 1L;
        Transaction addCustomer = new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" );
        addCustomer.execute();

        assertThrows( IllegalArgumentException.class, () -> new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ) );
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
    void testDeleteCustomerThatDoesNotExists() {
        assertThrows( IllegalArgumentException.class, () -> new DeleteCustomerTransaction( 1L ) );
    }

    @Test
    void testDeleteCustomerWithReservation() {
        Long customerId = 1L;
        new AddCustomerTransaction( customerId, "Kiddo", "Zahlt" ).execute();

        Long tableId = 1L;
        new AddTableTransaction( tableId ).execute();

        new ScheduledReservationTransaction( tableId, customerId, LocalDate.now(), LocalTime.now() ).execute();

        assertThrows( IllegalArgumentException.class, () -> new DeleteCustomerTransaction( customerId ) );
    }
}