package org.thekiddos.manager.services;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.api.model.MessageDTO;
import org.thekiddos.manager.models.Message;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.SendMessageToManagerTransaction;
import org.thekiddos.manager.transactions.SendMessageToWaiterTransaction;
import org.thekiddos.manager.transactions.SendMessageTransaction;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class WaiterChatServiceTest {
    private final WaiterChatService waiterChatService;

    @Autowired
    public WaiterChatServiceTest( WaiterChatService waiterChatService ) {
        this.waiterChatService = waiterChatService;
    }

    @BeforeEach
    void setUp() {
        waiterChatService.clearPendingMessages();
        Database.deleteMessages();
    }

    @Test
    void testSetAcknowledged() {
        waiterChatService.setAcknowledged();
        assertTrue( waiterChatService.isOnline() );
        assertTrue( waiterChatService.isAcknowledgedNow() );
    }

    @SneakyThrows
    @Test
    void testTrySetAcknowledgementTimedOut() {
        waiterChatService.setAcknowledged();
        // before TimeOut of five seconds
        assertFalse( waiterChatService.trySetAcknowledgementTimedOut() );
        Thread.sleep( WaiterChatService.TIME_OUT_DURATION.toMillis() );
        // After TimeOut
        assertTrue( waiterChatService.trySetAcknowledgementTimedOut() );
        assertFalse( waiterChatService.isOnline() );
        assertFalse( waiterChatService.isAcknowledgedNow() );
    }

    @SneakyThrows
    @Test
    void testAddMessageWhenOffline() {
        SendMessageTransaction sendMessageTransaction = new SendMessageToWaiterTransaction( "WTF!" );
        Thread.sleep( 5000 );
        waiterChatService.trySetAcknowledgementTimedOut();
        assertThrows( IllegalStateException.class, () -> waiterChatService.addPendingMessage( sendMessageTransaction.getMessage() ) );
    }

    @Test
    void testAddMessage() {
        SendMessageTransaction sendMessageTransaction = new SendMessageToWaiterTransaction( "WTF!" );
        Message message = sendMessageTransaction.getMessage();
        waiterChatService.setAcknowledged();
        waiterChatService.addPendingMessage( message );

        assertEquals( 1, waiterChatService.getPendingMessagesNumber() );
        assertEquals( message, waiterChatService.getPendingMessageByIndex( 0 ) );

        List<MessageDTO> messages = waiterChatService.getPendingMessages();
        assertEquals( 1, messages.size() );

        MessageDTO actual = messages.get( 0 );
        assertEquals( message.getContents(), actual.getContents() );
        assertEquals( message.getSender(), actual.getSender() );
        assertEquals( message.getReceiver(), actual.getReceiver() );
        assertEquals( message.isSeen(), actual.isSeen() );
        assertEquals( message.getCreatedAt(), actual.getCreatedAt() );
    }

    @Test
    void testClearMessages() {
        SendMessageTransaction sendMessageTransaction = new SendMessageToWaiterTransaction( "WTF!" );
        Message message = sendMessageTransaction.getMessage();
        waiterChatService.setAcknowledged();
        waiterChatService.addPendingMessage( message );

        waiterChatService.clearPendingMessages();
        assertEquals( 0, waiterChatService.getPendingMessagesNumber() );
    }

    @Test
    void testGetMessages() {
        SendMessageTransaction sendMessageToWaiter = new SendMessageToWaiterTransaction( "Hi Kids" );
        sendMessageToWaiter.execute();
        Message messageToWaiter = sendMessageToWaiter.getMessage();

        SendMessageTransaction sendMessageToManager = new SendMessageToManagerTransaction( "Go to Hell!" );
        sendMessageToManager.execute();
        Message messageToManager = sendMessageToManager.getMessage();

        List<MessageDTO> messages = waiterChatService.getAllMessages();
        assertEquals( 2, messages.size() );

        MessageDTO waiterActual = messages.stream().filter( messageDTO -> messageDTO.getReceiver().equals( Util.CHAT_USER_WAITER ) ).collect( Collectors.toList() ).get( 0 );
        assertEquals( messageToWaiter.getContents(), waiterActual.getContents() );
        assertEquals( messageToWaiter.getSender(), waiterActual.getSender() );
        assertEquals( messageToWaiter.getReceiver(), waiterActual.getReceiver() );
        assertEquals( messageToWaiter.isSeen(), waiterActual.isSeen() );
        assertEquals( messageToWaiter.getCreatedAt(), waiterActual.getCreatedAt() );

        MessageDTO managerActual = messages.stream().filter( messageDTO -> messageDTO.getReceiver().equals( Util.CHAT_USER_MANAGER ) ).collect( Collectors.toList() ).get( 0 );

        assertEquals( messageToManager.getContents(), managerActual.getContents() );
        assertEquals( messageToManager.getSender(), managerActual.getSender() );
        assertEquals( messageToManager.getReceiver(), managerActual.getReceiver() );
        assertEquals( messageToManager.isSeen(), managerActual.isSeen() );
        assertEquals( messageToManager.getCreatedAt(), managerActual.getCreatedAt() );
    }
}