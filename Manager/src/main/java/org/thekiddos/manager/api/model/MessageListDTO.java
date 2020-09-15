package org.thekiddos.manager.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageListDTO {
    private List<MessageDTO> messages;
}
