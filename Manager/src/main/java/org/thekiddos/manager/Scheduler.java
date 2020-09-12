package org.thekiddos.manager;

import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thekiddos.manager.gui.controllers.GUIController;
import org.thekiddos.manager.gui.models.WindowContainer;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.services.WaiterChatService;
import org.thekiddos.manager.transactions.DeleteReservationTransaction;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Scheduler {
    private final GUIController guiController;
    private final WaiterChatService waiterChatService;

    @Autowired
    public Scheduler( GUIController guiController, WaiterChatService waiterChatService ) {
        this.guiController = guiController;
        this.waiterChatService = waiterChatService;
    }

    // This can be called with something like a REST Controller instead of constantly invoking
    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    void refreshGUIWindows() {
        if ( Util.isGuiInitialized() ) {
            Platform.runLater( () -> {
                for ( WindowContainer windowContainer : Util.getWindowContainers() )
                    windowContainer.getController().refresh();
            });
        }
        else {
            log.warn( "GUI not initialized. Testing Mode is assumed." );
        }
    }

    @Scheduled(initialDelay = 15000, fixedDelay = 900000) // 15 Minutes
    void handleOverdueReservations() {
        List<Reservation> overdueReservations = Database.getReservations().stream().filter( Reservation::isOverdue ).collect( Collectors.toList() );

        // I was to lazy to do something nice with this, a proper way would be create a gui, fill it with theses reservations and promote the user for an action
        for ( Reservation reservation : overdueReservations )
            new DeleteReservationTransaction( reservation ).execute();
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 2500)
    void trySetAcknowledgementTimedOut() {
        waiterChatService.trySetAcknowledgementTimedOut();
    }

    // TODO add a method to checkout active reservations from the previous days
}
