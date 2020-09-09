package org.thekiddos.manager.transactions;

import lombok.Getter;
import org.thekiddos.manager.models.Message;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDateTime;

public abstract class SendMessageTransaction implements Transaction {
    @Getter
    private Message message;

    public SendMessageTransaction( String contents ) {
        this.message = new Message( contents, getSender(), getReceiver(), LocalDateTime.now(), false );
    }

    @Override
    public void execute() {
        Database.addMessage( message );
    }

    abstract String getSender();
    abstract String getReceiver();
}
