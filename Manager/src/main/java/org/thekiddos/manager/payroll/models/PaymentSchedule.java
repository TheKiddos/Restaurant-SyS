package org.thekiddos.manager.payroll.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PaymentSchedule {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    public Long id;

    public abstract boolean isPayDay( LocalDate payDay );

    public abstract LocalDate getStartPayDay( LocalDate payDay );
}
