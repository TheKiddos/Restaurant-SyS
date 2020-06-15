package org.thekiddos.manager.models;

import org.thekiddos.manager.PrintPayCheckTransaction;

public class HoldMethod implements PaymentMethod {
    @Override
    public void pay( PayCheck payCheck ) {
        PrintPayCheckTransaction check = new PrintPayCheckTransaction( payCheck.getEmployee(), payCheck.getAmount(), payCheck.getPayDate() );
        check.execute();
    }

    @Override
    public String getDetails() {
        return "Printed";
    }
}
