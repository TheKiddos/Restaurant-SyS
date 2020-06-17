package org.thekiddos.manager;

import org.junit.jupiter.api.Test;
import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.repositories.Database;

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
}
