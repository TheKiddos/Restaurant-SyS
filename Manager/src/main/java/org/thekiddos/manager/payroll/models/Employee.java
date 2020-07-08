package org.thekiddos.manager.payroll.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Employee {
    @NonNull @Id
    private Long id;
    @NonNull
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private PaymentClassification paymentClassification;
    @OneToOne(cascade = CascadeType.ALL)
    private PaymentSchedule paymentSchedule;
    @OneToOne(cascade = CascadeType.ALL)
    private PaymentMethod paymentMethod;

    public boolean isPayDay( LocalDate payDay ) {
        return paymentSchedule.isPayDay( payDay );
    }

    public PayCheck payDay( LocalDate payDate ) {
        LocalDate startPayDay = paymentSchedule.getStartPayDay( payDate );
        double amount = paymentClassification.calculatePay( startPayDay, payDate );
        PayCheck payCheck = new PayCheck( payDate, amount, paymentMethod.getDetails(), this );
        paymentMethod.pay( payCheck );
        return payCheck;
    }
}
