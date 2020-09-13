package org.thekiddos.manager.services;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.thekiddos.manager.models.Message;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WaiterChatService {
    public static final Duration TIME_OUT_DURATION = Duration.ofSeconds( 5 );
    @Getter
    private boolean online = false;
    private LocalDateTime lastAcknowledgementTimeStamp;
    private final List<Message> pendingMessages = new ArrayList<>();

    public void setAcknowledged() {
        online = true;
        lastAcknowledgementTimeStamp = LocalDateTime.now();
    }

    public boolean trySetAcknowledgementTimedOut() {
        boolean timedOut = isAcknowledgementTimedOut();
        if ( timedOut )
            online = false;
        return timedOut;
    }

    public boolean isAcknowledgementTimedOut() {
        if ( !online )
            return true;
        return Duration.between( lastAcknowledgementTimeStamp, LocalDateTime.now() ).compareTo( TIME_OUT_DURATION ) > 0;
    }

    public boolean isAcknowledgedNow() {
        if ( !online )
            return false;
        return Duration.between( lastAcknowledgementTimeStamp, LocalDateTime.now() ).toMinutes() == 0;
    }

    /**
     * Adds a message to the pending list of messages for the waiter.
     * @param message - The message to add.
     * @throws IllegalStateException - if the Waiter is offline.
     */
    public void addPendingMessage( Message message ) {
        if ( !online )
            throw new IllegalStateException( "Can't add pending message when waiter is offline" );
        pendingMessages.add( message );
    }

    public int getPendingMessagesNumber() {
        return pendingMessages.size();
    }

    /**
     * Returns the message at the specified position.
     * @param index – index of the element to return
     * @throws IndexOutOfBoundsException – if the index is out of range (index < 0 || index >= size())
     * @return the message at the specified position.
     */
    public Message getPendingMessageByIndex( int index ) {
        return pendingMessages.get( index );
    }

    public void clearPendingMessages() {
        pendingMessages.clear();
    }
}
