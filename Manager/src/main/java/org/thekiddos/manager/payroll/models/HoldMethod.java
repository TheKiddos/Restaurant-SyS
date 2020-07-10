package org.thekiddos.manager.payroll.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class HoldMethod extends PaymentMethod {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Override
    public void pay( PayCheck payCheck ) {
        // TODO what to do here (Saving the paycheck to database? and remove this call from Employee)
    }

    @Override
    public String getDetails() {
        return "Printed";
    }

    @Override
    public String toString() {
        return "Payment Delivered through the paymaster";
    }
}
