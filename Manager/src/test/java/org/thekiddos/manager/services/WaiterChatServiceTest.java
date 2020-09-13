package org.thekiddos.manager.services;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.models.Message;
import org.thekiddos.manager.transactions.SendMessageToWaiterTransaction;
import org.thekiddos.manager.transactions.SendMessageTransaction;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class WaiterChatServiceTest {
    @Test
    void testSetAcknowledged() {
        WaiterChatService waiterChatService = new WaiterChatService();
        waiterChatService.setAcknowledged();
        assertTrue( waiterChatService.isOnline() );
        assertTrue( waiterChatService.isAcknowledgedNow() );
    }

    @SneakyThrows
    @Test
    void testTrySetAcknowledgementTimedOut() {
        WaiterChatService waiterChatService = new WaiterChatService();
        waiterChatService.setAcknowledged();
        // before TimeOut of five seconds
        assertFalse( waiterChatService.trySetAcknowledgementTimedOut() );
        Thread.sleep( WaiterChatService.TIME_OUT_DURATION.toMillis() );
        // After TimeOut
        assertTrue( waiterChatService.trySetAcknowledgementTimedOut() );
        assertFalse( waiterChatService.isOnline() );
        assertFalse( waiterChatService.isAcknowledgedNow() );
    }

    @Test
    void testAddMessageWhenOffline() {
        WaiterChatService waiterChatService = new WaiterChatService();
        SendMessageTransaction sendMessageTransaction = new SendMessageToWaiterTransaction( "WTF!" );
        assertThrows( IllegalStateException.class, () -> waiterChatService.addPendingMessage( sendMessageTransaction.getMessage() ) );
    }

    @Test
    void testAddMessage() {
        WaiterChatService waiterChatService = new WaiterChatService();
        SendMessageTransaction sendMessageTransaction = new SendMessageToWaiterTransaction( "WTF!" );
        Message message = sendMessageTransaction.getMessage();
        waiterChatService.setAcknowledged();
        waiterChatService.addPendingMessage( message );

        assertEquals( 1, waiterChatService.getPendingMessagesNumber() );
        assertEquals( message, waiterChatService.getPendingMessageByIndex( 0 ) );
    }

    @Test
    void testClearMessages() {
        WaiterChatService waiterChatService = new WaiterChatService();
        SendMessageTransaction sendMessageTransaction = new SendMessageToWaiterTransaction( "WTF!" );
        Message message = sendMessageTransaction.getMessage();
        waiterChatService.setAcknowledged();
        waiterChatService.addPendingMessage( message );

        waiterChatService.clearPendingMessages();
        assertEquals( 0, waiterChatService.getPendingMessagesNumber() );
    }
}