package org.thekiddos.manager.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageListDTO {
    private boolean isManagerOnline;
    private List<MessageDTO> messages;

    public MessageListDTO( List<MessageDTO> messages ) {
        isManagerOnline = false;
        this.messages = messages;
    }
}
