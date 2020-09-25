package org.thekiddos.manager.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.api.mapper.MessageMapper;
import org.thekiddos.manager.api.model.MessageDTO;
import org.thekiddos.manager.models.Message;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.services.WaiterChatServiceImpl;
import org.thekiddos.manager.transactions.SendMessageToManagerTransaction;
import org.thekiddos.manager.transactions.SendMessageToWaiterTransaction;
import org.thekiddos.manager.transactions.SendMessageTransaction;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
public class WaiterChatControllerTest {
    private final WaiterChatController waiterChatController;
    @MockBean
    private WaiterChatServiceImpl waiterChatService;
    private final MockMvc mockMvc;
    private final MessageMapper messageMapper;
    private final ObjectMapper objectToJsonMapper;

    @Autowired
    public WaiterChatControllerTest( WaiterChatController waiterChatController, MessageMapper messageMapper, ObjectMapper objectToJsonMapper ) {
        this.waiterChatController = waiterChatController;
        mockMvc = MockMvcBuilders.standaloneSetup( waiterChatController ).build();
        this.messageMapper = messageMapper;
        this.objectToJsonMapper = objectToJsonMapper;
    }

    @BeforeEach
    void setUpDatabase() {
        Database.deleteMessages();
    }

    @Test
    void testProcessAcknowledgmentWithoutMessages() throws Exception {
        // isManagerOnline is not important for the server, it's for the client only.
        mockMvc.perform( post( "/api/chat" ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$[*]", hasSize( 2 ) ) )
                .andExpect( jsonPath( "$['managerOnline']", is( false ) ) )
                .andExpect( jsonPath( "$['messages']", hasSize( 0 ) ) );
        Mockito.verify( waiterChatService, Mockito.times( 1 ) ).setAcknowledged();
        /*
        // Since we already know that waiterChatService.setAcknowledged() does it's job in another test
        // we only want to know that the controller calls it so we don't need the following:
        assertTrue( waiterChatService.isOnline() );
        assertTrue( waiterChatService.isAcknowledgedNow() );
        // The rest of the tests will use the same idea but without this comment.
         */
    }

    @Test
    void testProcessAcknowledgmentWithMessages() throws Exception {
        // I used with nano 0 to stick with the problem of database can't save the same precision as java.
        MessageDTO unreadMessage = new MessageDTO( "Hello", Util.CHAT_USER_WAITER, Util.CHAT_USER_MANAGER, LocalDateTime.now().withNano( 0 ), false );
        MessageDTO readMessage = new MessageDTO( "Go to HELL!", Util.CHAT_USER_MANAGER, Util.CHAT_USER_WAITER, LocalDateTime.now().withNano( 0 ), true );

        // Waiter Should Be online when receiving messages
        Mockito.when( waiterChatService.getPendingMessages() ).thenReturn( Arrays.asList( unreadMessage, readMessage ) );

        mockMvc.perform( post( "/api/chat" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$[*]", hasSize( 2 ) ) )
                .andExpect( jsonPath( "$['managerOnline']", is( false ) ) )
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

        Mockito.verify( waiterChatService, Mockito.times( 1 ) ).setAcknowledged();
        Mockito.verify( waiterChatService, Mockito.times( 1 ) ).clearPendingMessages();
    }

    @SneakyThrows
    @Test
    void testGetMessages() {
        SendMessageTransaction sendMessageToWaiter = new SendMessageToWaiterTransaction( "Hi Kids" );
        sendMessageToWaiter.execute();
        Message messageToWaiter = sendMessageToWaiter.getMessage();
        messageToWaiter.setSeen(); // The message will be read

        SendMessageTransaction sendMessageToManager = new SendMessageToManagerTransaction( "Go to Hell!" );
        sendMessageToManager.execute();
        Message messageToManager = sendMessageToManager.getMessage();

        List<MessageDTO> messages = Stream.of( messageToWaiter, messageToManager ).map( messageMapper::messageToMessageDTO ).collect( Collectors.toList() );
        Mockito.when( waiterChatService.getAllMessages() ).thenReturn( messages );

        mockMvc.perform( get( "/api/chat/messages" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$[*]", hasSize( 2 ) ) )
                .andExpect( jsonPath( "$['managerOnline']", is( false ) ) )
                .andExpect( jsonPath( "$['messages']", hasSize( 2 ) ) )
                .andExpect( jsonPath( "$['messages'][0].contents", is( messageToWaiter.getContents() ) ) )
                .andExpect( jsonPath( "$['messages'][0].sender", is( messageToWaiter.getSender() ) ) )
                .andExpect( jsonPath( "$['messages'][0].receiver", is( messageToWaiter.getReceiver() ) ) )
                .andExpect( jsonPath( "$['messages'][0].createdAt", is( messageToWaiter.getCreatedAt().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd hh:mm:ss" ) ) ) ) )
                .andExpect( jsonPath( "$['messages'][0].seen", is( messageToWaiter.isSeen() ) ) )
                .andExpect( jsonPath( "$['messages'][1].contents", is( messageToManager.getContents() ) ) )
                .andExpect( jsonPath( "$['messages'][1].sender", is( messageToManager.getSender() ) ) )
                .andExpect( jsonPath( "$['messages'][1].receiver", is( messageToManager.getReceiver() ) ) )
                .andExpect( jsonPath( "$['messages'][1].createdAt", is( messageToManager.getCreatedAt().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd hh:mm:ss" ) ) ) ) )
                .andExpect( jsonPath( "$['messages'][1].seen", is( messageToManager.isSeen() ) ) );

        // Waiter has seen messages sent to him
        Database.getMessages().stream().filter( message -> message.getReceiver().equals( Util.CHAT_USER_WAITER ) ).forEach( message -> assertTrue( message.isSeen() ) );
        Mockito.verify( waiterChatService, Mockito.times( 1 ) ).setAcknowledged();
    }

    @Test
    void testPostMessage() throws Exception {
        MessageDTO messageToManager = new MessageDTO();
        messageToManager.setContents( "Die!" );
        String messageJson = objectToJsonMapper.writeValueAsString( messageToManager );

        assertEquals( 0, Database.getMessages().size() );

        mockMvc.perform( post( "/api/chat/send" ).content( messageJson ).contentType( "application/json;charset=UTF-8" ) )
                .andExpect( status().isCreated() )
                .andExpect( content().string( containsString( "Message Not Read" ) ) );

        assertEquals( 1, Database.getMessages().size() );
        Message message = Database.getMessages().get( 0 );
        assertEquals( messageToManager.getContents(), message.getContents() );
        assertEquals( Util.CHAT_USER_MANAGER, message.getReceiver() );
        assertEquals( Util.CHAT_USER_WAITER, message.getSender() );
        assertFalse( message.isSeen() );
        assertEquals( 0, Duration.between( message.getCreatedAt(), LocalDateTime.now() ).toMinutes() );
    }
}
