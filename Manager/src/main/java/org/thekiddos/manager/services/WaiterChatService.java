package org.thekiddos.manager.services;

import org.thekiddos.manager.api.model.MessageListDTO;
import org.thekiddos.manager.models.Message;

import java.time.Duration;

public interface WaiterChatService {

    Duration TIME_OUT_DURATION = Duration.ofSeconds( 5 );

    void setAcknowledged();

    boolean trySetAcknowledgementTimedOut();

    boolean isAcknowledgementTimedOut();

    boolean isAcknowledgedNow();

    void addPendingMessage( Message message );

    int getPendingMessagesNumber();

    Message getPendingMessageByIndex( int index );

    void clearPendingMessages();

    MessageListDTO getPendingMessages();

    boolean isOnline();
}
