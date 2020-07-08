package org.thekiddos.manager.payroll.models;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

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
}
