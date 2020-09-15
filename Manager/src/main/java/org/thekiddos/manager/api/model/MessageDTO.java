package org.thekiddos.manager.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    @NonNull
    private String contents;
    @NonNull
    private String sender;
    @NonNull
    private String receiver;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean seen = false;
}
