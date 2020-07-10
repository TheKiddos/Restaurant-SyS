package org.thekiddos.manager.payroll.models;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
public abstract class PaymentClassification {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long id;

    public abstract double calculatePay( LocalDate startData, LocalDate endDate );

    public abstract String getType();

    public abstract String getBaseSalary();

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        PaymentClassification that = (PaymentClassification) o;
        return id.equals( that.id );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }
}
