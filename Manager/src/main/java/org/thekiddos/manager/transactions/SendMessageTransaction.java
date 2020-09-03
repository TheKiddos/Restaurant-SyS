package org.thekiddos.manager.transactions;

import lombok.AllArgsConstructor;
import org.thekiddos.manager.models.Message;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDateTime;

@AllArgsConstructor
public abstract class SendMessageTransaction implements Transaction {
    private String contents;

    @Override
    public void execute() {
        Message message = new Message( contents, getSender(), getReceiver(), LocalDateTime.now(), false );
        Database.addMessage( message );
    }

    abstract String getSender();
    abstract String getReceiver();
}
