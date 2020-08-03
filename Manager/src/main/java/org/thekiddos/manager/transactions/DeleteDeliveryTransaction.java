package org.thekiddos.manager.transactions;

import lombok.Getter;
import org.thekiddos.manager.models.Delivery;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;

public class DeleteDeliveryTransaction implements Transaction {
    @Getter
    private final Delivery delivery;

    public DeleteDeliveryTransaction( Long customerId, LocalDate date ) {
        this.delivery = Database.getDeliveryById( customerId, date );

        if ( delivery == null )
            throw new IllegalArgumentException( "No deliveries for the selected customer were found" );
    }

    @Override
    public void execute() {
        Database.deleteDelivery( delivery );
    }
}
