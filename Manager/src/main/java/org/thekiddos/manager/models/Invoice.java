package org.thekiddos.manager.models;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class Invoice {
    private double total, orderTotal, tableFee, reservationFee, discount, netAmount;
    private Long tableId, customerId;
    private LocalDate date = LocalDate.now();
    private LocalTime time = LocalTime.now();
    private List<Item> items;
}
