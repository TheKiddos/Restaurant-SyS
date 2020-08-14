package org.thekiddos.manager;

import javafx.application.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thekiddos.manager.gui.controllers.GUIController;
import org.thekiddos.manager.gui.models.WindowContainer;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.DeleteReservationTransaction;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Scheduler {
    private GUIController guiController;

    @Autowired
    public Scheduler( GUIController guiController ) {
        this.guiController = guiController;
    }

    // This can be called with something like a REST Controller instead of constantly invoking
    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    private void refreshGUIWindows() {
        Platform.runLater( () -> {
            for ( WindowContainer windowContainer : Util.getWindowContainers() )
                windowContainer.getController().refresh();
        });
    }

    @Scheduled(initialDelay = 15000, fixedDelay = 900000) // 15 Minutes
    private void handleOverdueReservations() {
        List<Reservation> overdueReservations = Database.getReservations().stream().filter( Reservation::isOverdue ).collect( Collectors.toList() );

        // I was to lazy to do something nice with this, a proper way would be create a gui, fill it with theses reservations and promote the user for an action
        for ( Reservation reservation : overdueReservations )
            new DeleteReservationTransaction( reservation ).execute();
    }
}
