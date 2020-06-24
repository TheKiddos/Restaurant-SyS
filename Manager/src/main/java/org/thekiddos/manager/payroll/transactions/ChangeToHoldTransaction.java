package org.thekiddos.manager.payroll.transactions;

import org.thekiddos.manager.payroll.models.HoldMethod;
import org.thekiddos.manager.payroll.models.PaymentMethod;

public class ChangeToHoldTransaction extends ChangeMethodTransaction {
    public ChangeToHoldTransaction( Long empId ) {
        super( empId );
    }

    @Override
    protected PaymentMethod getPaymentMethod() {
        return new HoldMethod();
    }
}
