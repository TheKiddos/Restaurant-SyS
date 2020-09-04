package org.thekiddos.manager.transactions;

import org.thekiddos.manager.models.Message;
import org.thekiddos.manager.repositories.Database;

import java.util.List;

/**
 * Used to mark messages as read/seen
 */
public class ReadMessagesTransaction implements Transaction {
    private final String username;

    /**
     * @param username The username that read/seen the messages
     *                 all the messages that are sent to this user will be marked as read/seen
     */
    public ReadMessagesTransaction( String username ) {
        this.username = username;
    }

    @Override
    public void execute() {
        List<Message> messages = Database.getMessages();
        for ( Message message : messages )
            if ( message.getReceiver().equals( username ) ) {
                message.setSeen();
                Database.addMessage( message );
            }
    }
}
