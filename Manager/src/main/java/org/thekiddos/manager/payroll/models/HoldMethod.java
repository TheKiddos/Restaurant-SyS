package org.thekiddos.manager.payroll.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class HoldMethod extends PaymentMethod {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

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

    @Override
    public String toString() {
        return "Payment Delivered through the paymaster";
    }
}
