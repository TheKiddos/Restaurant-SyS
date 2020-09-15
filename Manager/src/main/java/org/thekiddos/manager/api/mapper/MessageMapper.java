package org.thekiddos.manager.api.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.thekiddos.manager.api.model.MessageDTO;
import org.thekiddos.manager.models.Message;

@Mapper(componentModel = "spring")
@Component
public interface MessageMapper {

    MessageDTO messageToMessageDTO( Message message );

    default Message messageDTOToMessage( MessageDTO messageDTO ) {
        return new Message( messageDTO.getContents(),
                messageDTO.getSender(),
                messageDTO.getReceiver(),
                messageDTO.getCreatedAt(),
                messageDTO.isSeen()
        );
    }
}
