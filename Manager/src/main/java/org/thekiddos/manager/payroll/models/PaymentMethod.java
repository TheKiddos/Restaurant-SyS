package org.thekiddos.manager.payroll.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PaymentMethod {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    public abstract void pay( PayCheck payCheck );

    public abstract String getDetails();

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        PaymentMethod that = (PaymentMethod) o;
        return id.equals( that.id );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }
}
