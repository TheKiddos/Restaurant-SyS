package org.thekiddos.manager.services;

import org.thekiddos.manager.api.model.MessageDTO;
import org.thekiddos.manager.models.Message;

import java.time.Duration;
import java.util.List;

public interface WaiterChatService {

    Duration TIME_OUT_DURATION = Duration.ofSeconds( 5 );

    List<MessageDTO> getAllMessages();

    void setAcknowledged();

    boolean trySetAcknowledgementTimedOut();

    boolean isAcknowledgementTimedOut();

    boolean isAcknowledgedNow();

    void addPendingMessage( Message message );

    int getPendingMessagesNumber();

    Message getPendingMessageByIndex( int index );

    void clearPendingMessages();

    List<MessageDTO> getPendingMessages();

    boolean isOnline();
}
