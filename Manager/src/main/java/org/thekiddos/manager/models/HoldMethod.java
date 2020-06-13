package org.thekiddos.manager.models;

public class HoldMethod implements PaymentMethod {
    @Override
    public void pay( PayCheck payCheck ) {
        // TODO this should use a printer or something
    }

    @Override
    public String getDetails() {
        return "Printed";
    }
}
