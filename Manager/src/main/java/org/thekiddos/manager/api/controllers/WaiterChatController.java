package org.thekiddos.manager.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thekiddos.manager.services.WaiterChatService;

@Controller
@RequestMapping("/api/chat")
public class WaiterChatController {
    private final WaiterChatService waiterChatService;

    @Autowired
    public WaiterChatController( WaiterChatService waiterChatService ) {
        this.waiterChatService = waiterChatService;
    }

    @PostMapping
    private void processAcknowledgment() {
        waiterChatService.setAcknowledged();
    }
}
