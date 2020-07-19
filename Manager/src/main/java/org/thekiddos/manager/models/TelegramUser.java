package org.thekiddos.manager.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.thekiddos.manager.bot.Commands;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table( name = "telegram_users" )
public class TelegramUser {
    @Id @NonNull
    private Integer id;

    private String lastCommand = Commands.NOTHING;
    private String email;
    private Integer verificationCode;
    private boolean isVerified = false;
}
