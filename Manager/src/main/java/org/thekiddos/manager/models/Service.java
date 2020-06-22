package org.thekiddos.manager.models;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Service {
    protected Long customerId;
    protected LocalDate serviceDate;
    protected LocalTime serviceTime;

    public Service( Long customerId, LocalDate serviceDate, LocalTime serviceTime ) {
        this.customerId = customerId;
        this.serviceDate = serviceDate;
        this.serviceTime = serviceTime;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public java.time.LocalDate getDate() {
        return this.serviceDate;
    }

    public java.time.LocalTime getTime() {
        return this.serviceTime;
    }
}
