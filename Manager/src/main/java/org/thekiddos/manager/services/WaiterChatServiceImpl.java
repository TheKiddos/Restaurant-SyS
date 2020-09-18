package org.thekiddos.manager.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thekiddos.manager.api.mapper.MessageMapper;
import org.thekiddos.manager.api.model.MessageDTO;
import org.thekiddos.manager.models.Message;
import org.thekiddos.manager.repositories.Database;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaiterChatServiceImpl implements WaiterChatService {
    @Getter
    private boolean online = false;
    private LocalDateTime lastAcknowledgementTimeStamp;
    private final List<Message> pendingMessages = Collections.synchronizedList( new ArrayList<>() );
    private final MessageMapper messageMapper;

    @Autowired
    public WaiterChatServiceImpl( MessageMapper messageMapper ) {
        this.messageMapper = messageMapper;
    }

    @Override
    public List<MessageDTO> getAllMessages() {
        return Database.getMessages().stream().map( messageMapper::messageToMessageDTO ).collect( Collectors.toList() );
    }

    @Override
    public void setAcknowledged() {
        online = true;
        lastAcknowledgementTimeStamp = LocalDateTime.now();
    }

    @Override
    public boolean trySetAcknowledgementTimedOut() {
        boolean timedOut = isAcknowledgementTimedOut();
        if ( timedOut )
            online = false;
        return timedOut;
    }

    @Override
    public boolean isAcknowledgementTimedOut() {
        if ( !online )
            return true;
        return Duration.between( lastAcknowledgementTimeStamp, LocalDateTime.now() ).compareTo( WaiterChatService.TIME_OUT_DURATION ) > 0;
    }

    @Override
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
    @Override
    public void addPendingMessage( Message message ) {
        if ( !online )
            throw new IllegalStateException( "Can't add pending message when waiter is offline" );
        pendingMessages.add( message );
    }

    @Override
    public int getPendingMessagesNumber() {
        return pendingMessages.size();
    }

    /**
     * Returns the message at the specified position.
     * @param index – index of the element to return
     * @throws IndexOutOfBoundsException – if the index is out of range (index < 0 || index >= size())
     * @return the message at the specified position.
     */
    @Override
    public Message getPendingMessageByIndex( int index ) {
        return pendingMessages.get( index );
    }

    @Override
    public void clearPendingMessages() {
        pendingMessages.clear();
    }

    @Override
    public List<MessageDTO> getPendingMessages() {
        return pendingMessages.stream().map( messageMapper::messageToMessageDTO ).collect( Collectors.toList() );
    }
}
