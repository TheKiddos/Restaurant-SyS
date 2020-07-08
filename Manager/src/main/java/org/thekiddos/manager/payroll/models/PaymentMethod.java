package org.thekiddos.manager.payroll.models;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PaymentMethod {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    public abstract void pay( PayCheck payCheck );

    public abstract String getDetails();
}
