package org.thekiddos.manager.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.services.WaiterChatService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void testProcessAcknowledgment() throws Exception {
        mockMvc.perform( post( "/api/chat" ) ).andExpect( status().isOk() );
        assertTrue( waiterChatService.isOnline() );
        assertTrue( waiterChatService.isAcknowledgedNow() );
    }
}
