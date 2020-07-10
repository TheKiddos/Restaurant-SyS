package org.thekiddos.manager.payroll.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PaymentSchedule {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    public Long id;

    public abstract boolean isPayDay( LocalDate payDay );

    public abstract LocalDate getStartPayDay( LocalDate payDay );

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        PaymentSchedule that = (PaymentSchedule) o;
        return id.equals( that.id );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }
}
