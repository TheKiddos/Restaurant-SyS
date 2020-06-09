package org.thekiddos.manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PayrollTest {
    @Test
    void testAddSalariedEmployee() {
        int empId = 1;
        Transaction addSalariedEmployee = new AddSalariedEmployee( empId, "Zahlt", 1000.0 );
        addSalariedEmployee.execute();

        Employee emp = Database.getEmployeeById( empId );
        assertEquals( "Zahlt", emp.getName() );

        PaymentClassification paymentClassification = emp.getPaymentClassification();
        SalariedClassification salariedClassification = (SalariedClassification)paymentClassification;
        assertNotNull( paymentClassification );
        assertEquals( 1000.0, paymentClassification.getSalary() );

        PaymentSchedule paymentSchedule = emp.getPaymentSchedule();
        MonthlySchedule monthlySchedule = (MonthlySchedule)paymentSchedule;

        PaymentMethod paymentMethod = emp.getPaymentMethod();
        HoldMethod holdMethod = (HoldMethod)paymentMethod;
        assertNotNull( holdMethod );
    }
}