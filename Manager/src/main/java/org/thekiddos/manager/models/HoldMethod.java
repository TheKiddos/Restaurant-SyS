package org.thekiddos.manager.models;

public class HoldMethod implements PaymentMethod {
    @Override
    public void pay( PayCheck payCheck ) {
        // TODO remove these comments
        //PrintPayCheckTransaction check = new PrintPayCheckTransaction( payCheck.getEmployee(), payCheck.getAmount(), payCheck.getPayDate() );
        //check.execute();
    }

    @Override
    public String getDetails() {
        return "Printed";
    }
}
