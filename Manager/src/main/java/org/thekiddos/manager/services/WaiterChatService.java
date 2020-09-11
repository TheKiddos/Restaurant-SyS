package org.thekiddos.manager.services;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class WaiterChatService {
    public static final Duration TIME_OUT_DURATION = Duration.ofSeconds( 5 );
    @Getter
    private boolean online = false;
    private LocalDateTime lastAcknowledgementTimeStamp;

    public void setAcknowledged() {
        online = true;
        lastAcknowledgementTimeStamp = LocalDateTime.now();
    }

    public boolean trySetAcknowledgementTimedOut() {
        boolean timedOut = isAcknowledgementTimedOut();
        if ( timedOut )
            online = false;
        return timedOut;
    }

    public boolean isAcknowledgementTimedOut() {
        if ( !online )
            return true;
        return Duration.between( lastAcknowledgementTimeStamp, LocalDateTime.now() ).compareTo( TIME_OUT_DURATION ) > 0;
    }

    public boolean isAcknowledgedNow() {
        if ( !online )
            return false;
        return Duration.between( lastAcknowledgementTimeStamp, LocalDateTime.now() ).toMinutes() == 0;
    }
}
