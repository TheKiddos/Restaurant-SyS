package org.thekiddos.manager.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thekiddos.manager.models.Message;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.services.WaiterChatService;
import org.thekiddos.manager.transactions.SendMessageToWaiterTransaction;

import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith( SpringExtension.class )
@SpringBootTest
public class WaiterChatControllerTest {
    private final WaiterChatController waiterChatController;
    private final WaiterChatService waiterChatService;
    private final MockMvc mockMvc;

    @Autowired
    public WaiterChatControllerTest( WaiterChatController waiterChatController, WaiterChatService waiterChatService ) {
        this.waiterChatController = waiterChatController;
        this.waiterChatService = waiterChatService;
        mockMvc = MockMvcBuilders.standaloneSetup( waiterChatController ).build();
    }

    @BeforeEach
    void setUpDatabase() {
        Database.deleteMessages();
    }

    @Test
    void testProcessAcknowledgmentWithoutMessages() throws Exception {
        mockMvc.perform( post( "/api/chat" ) ).andExpect( status().isOk() );
        assertTrue( waiterChatService.isOnline() );
        assertTrue( waiterChatService.isAcknowledgedNow() );
    }

    @Test
    void testProcessAcknowledgmentWithMessages() throws Exception {
        Message unreadMessage = new SendMessageToWaiterTransaction( "Hello" ).getMessage();
        Message readMessage = new SendMessageToWaiterTransaction( "Go to Hell!" ).getMessage();
        readMessage.setSeen();

        // Waiter Should Be online when receiving messages
        waiterChatService.setAcknowledged();
        waiterChatService.addPendingMessage( unreadMessage );
        waiterChatService.addPendingMessage( readMessage );

        mockMvc.perform( post( "/api/chat" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$[*]", hasSize( 1 ) ) )
                .andExpect( jsonPath( "$['messages']", hasSize( 2 ) ) )
                .andExpect( jsonPath( "$['messages'][0].contents", is( unreadMessage.getContents() ) ) )
                .andExpect( jsonPath( "$['messages'][0].sender", is( unreadMessage.getSender() ) ) )
                .andExpect( jsonPath( "$['messages'][0].receiver", is( unreadMessage.getReceiver() ) ) )
                .andExpect( jsonPath( "$['messages'][0].createdAt", is( unreadMessage.getCreatedAt().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd hh:mm:ss" ) ) ) ) )
                .andExpect( jsonPath( "$['messages'][0].seen", is( unreadMessage.isSeen() ) ) )
                .andExpect( jsonPath( "$['messages'][1].contents", is( readMessage.getContents() ) ) )
                .andExpect( jsonPath( "$['messages'][1].sender", is( readMessage.getSender() ) ) )
                .andExpect( jsonPath( "$['messages'][1].receiver", is( readMessage.getReceiver() ) ) )
                .andExpect( jsonPath( "$['messages'][1].createdAt", is( readMessage.getCreatedAt().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd hh:mm:ss" ) ) ) ) )
                .andExpect( jsonPath( "$['messages'][1].seen", is( readMessage.isSeen() ) ) );

        assertTrue( waiterChatService.isOnline() );
        assertTrue( waiterChatService.isAcknowledgedNow() );
        assertEquals( 0, waiterChatService.getPendingMessagesNumber() );
    }
}
