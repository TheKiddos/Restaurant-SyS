package org.thekiddos.manager;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thekiddos.manager.repositories.Database;

@Component
public class StartUpAction {
    private final ApplicationContext applicationContext;

    public StartUpAction( ApplicationContext applicationContext ) {
        this.applicationContext = applicationContext;
    }

    @EventListener( ApplicationReadyEvent.class)
    public void setUpDatabase() {
        Database.setUpDatabase( applicationContext );
    }
}
