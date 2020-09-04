package org.thekiddos.manager.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class MessageTest {

    @BeforeEach
    void setUpDatabase() {
        Database.deleteMessages();
    }

    /**
     * Note this design seams like a stupid one when the number of users/roles grows
     * but if that happens, We can Simply use the Abstract class
     */

    @Test
    void testSendMessageToWaiter() {
        SendMessageTransaction sendMessageTransaction = new SendMessageToWaiterTransaction( "Get over here!" );
        sendMessageTransaction.execute();

        List<Message> messages = Database.getMessages();
        assertEquals( 1, messages.size() );

        Message message = messages.get( 0 );
        validateMessage( message, Util.CHAT_USER_WAITER, Util.CHAT_USER_MANAGER, "Get over here!", false );
    }

    @Test
    void testSendMessageToManager() {
        SendMessageTransaction sendMessageTransaction = new SendMessageToManagerTransaction( "Get over here!" );
        sendMessageTransaction.execute();

        List<Message> messages = Database.getMessages();
        assertEquals( 1, messages.size() );

        Message message = messages.get( 0 );
        validateMessage( message, Util.CHAT_USER_MANAGER, Util.CHAT_USER_WAITER, "Get over here!", false );
    }

    private void validateMessage( Message message, String receiver, String sender, String contents, boolean isSeen ) {
        assertEquals( receiver, message.getReceiver() );
        assertEquals( sender, message.getSender() );
        assertEquals( contents, message.getContents() );
        assertEquals( LocalDate.now(), message.getCreatedAt().toLocalDate() );
        assertEquals( LocalTime.now().getHour(), message.getCreatedAt().getHour() );
        assertEquals( isSeen, message.isSeen() );
    }

    @Test
    void testManagerReadMessages() {
        new SendMessageToManagerTransaction( "Hello" ).execute();
        new SendMessageToWaiterTransaction( "Hello" ).execute();
        new SendMessageToManagerTransaction( "Hello" ).execute();

        Transaction readMessagesTransaction = new ReadMessagesTransaction( Util.CHAT_USER_MANAGER );
        readMessagesTransaction.execute();

        List<Message> messages = Database.getMessages();
        for ( Message message : messages ) {
            assertEquals( message.getReceiver().equals( Util.CHAT_USER_MANAGER ), message.isSeen() ); // if the message is for the manager it should be read else it shouldn't
        }
    }

    @Test
    void testGetTodayMessages() {
        Message yesterdayMessage = new Message( "Hi", Util.CHAT_USER_MANAGER, Util.CHAT_USER_WAITER, LocalDateTime.now().minusDays( 1 ), false );
        Database.addMessage( yesterdayMessage );
        new SendMessageToWaiterTransaction( "Die" ).execute();

        List<Message> messages = Database.getMessagesAt( LocalDate.now() );
        assertEquals( 1, messages.size() );
        assertEquals( LocalDate.now(), messages.get( 0 ).getCreatedAt().toLocalDate() );
    }

}