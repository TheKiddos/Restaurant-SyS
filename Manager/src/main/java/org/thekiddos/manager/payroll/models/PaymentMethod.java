package org.thekiddos.manager.payroll.models;

public interface PaymentMethod {
    void pay( PayCheck payCheck );

    String getDetails();
}
