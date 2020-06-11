package org.thekiddos.manager;

public class ChangeToHoldTransaction extends ChangeMethodTransaction {
    public ChangeToHoldTransaction( Long empId ) {
        super( empId );
    }

    @Override
    protected PaymentMethod getPaymentMethod() {
        return new HoldMethod();
    }
}
