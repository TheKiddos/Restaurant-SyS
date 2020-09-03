package org.thekiddos.manager.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.SendMessageToWaiterTransaction;
import org.thekiddos.manager.transactions.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class MessageTest {

    @BeforeEach
    void setUpDatabase() {
        Database.deleteMessages();
    }

    @Test
    void testSendMessageToWaiter() {
        Transaction sendMessageTransaction = new SendMessageToWaiterTransaction( "Get over here!" );
        sendMessageTransaction.execute();

        List<Message> messages = Database.getMessages();
        assertEquals( 1, messages.size() );

        Message message = messages.get( 0 );
        assertEquals( "Waiter", message.getReceiver() );
        assertEquals( "Manager", message.getSender() );
        assertEquals( "Get over here!", message.getContents() );
        assertEquals( LocalDate.now(), message.getCreatedAt().toLocalDate() );
        assertEquals( LocalTime.now().getHour(), message.getCreatedAt().getHour() );
        assertFalse( message.isSeen() );
    }

}