package org.thekiddos.manager;

import org.thekiddos.manager.models.HoldMethod;
import org.thekiddos.manager.models.PaymentMethod;

public class ChangeToHoldTransaction extends ChangeMethodTransaction {
    public ChangeToHoldTransaction( Long empId ) {
        super( empId );
    }

    @Override
    protected PaymentMethod getPaymentMethod() {
        return new HoldMethod();
    }
}
