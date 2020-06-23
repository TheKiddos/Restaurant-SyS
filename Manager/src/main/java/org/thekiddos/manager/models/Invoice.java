package org.thekiddos.manager.models;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class Invoice {
    private double total, orderTotal, tableFee, reservationFee, discount, netAmount;
    private Long tableId, customerId;
    private LocalDate date = LocalDate.now();
    private LocalTime time = LocalTime.now();
    private Map<Item, Integer> items = new HashMap<>();
}
