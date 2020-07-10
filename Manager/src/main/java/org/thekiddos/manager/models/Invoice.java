package org.thekiddos.manager.models;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Invoice that contains all information about the reservation and ordered items.
 */
@Getter
public class Invoice {
    private final double total;
    private final double orderTotal;
    private final double tableFee;
    private final double reservationFee;
    private final double discount;
    private final double netAmount;
    private final Long tableId;
    private final Long customerId;
    private final LocalDate date = LocalDate.now();
    private final LocalTime time = LocalTime.now();
    private final Map<Item, Integer> items;

    public Invoice( Reservation reservation, double discount ) {
        customerId = reservation.getCustomerId();
        tableId = reservation.getTable().getId();
        reservationFee = reservation.getReservationFee();
        this.discount = discount;
        orderTotal = reservation.getOrder().getTotal();
        total = reservation.getTotal();
        tableFee = reservation.getTableFee();
        netAmount = reservation.getTotal() - discount;
        items = new HashMap<>( reservation.getOrder().getItems() );
    }
}
