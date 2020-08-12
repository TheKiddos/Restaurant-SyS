package org.thekiddos.manager.transactions;

import lombok.Getter;
import org.thekiddos.manager.models.Delivery;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;

/**
 * Deletes a {@link Delivery}
 */
public class DeleteDeliveryTransaction implements Transaction {
    @Getter
    private final Delivery delivery;

    /**
     * @param customerId the customer that ordered the delivery
     * @param date the date of the delivery to delete
     */
    public DeleteDeliveryTransaction( Long customerId, LocalDate date ) {
        this.delivery = Database.getDeliveryById( customerId, date );

        if ( delivery == null )
            throw new IllegalArgumentException( "No deliveries for the selected customer were found" );
    }

    /**
     * Deletes the delivery
     */
    @Override
    public void execute() {
        Database.deleteDelivery( delivery );
    }
}
