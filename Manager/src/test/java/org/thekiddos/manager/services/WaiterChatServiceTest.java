package org.thekiddos.manager.services;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class WaiterChatServiceTest {
    @Test
    void testSetAcknowledged() {
        WaiterChatService waiterChatService = new WaiterChatService();
        waiterChatService.setAcknowledged();
        assertTrue( waiterChatService.isOnline() );
        assertTrue( waiterChatService.isAcknowledgedNow() );
    }

    @SneakyThrows
    @Test
    void testTrySetAcknowledgementTimedOut() {
        WaiterChatService waiterChatService = new WaiterChatService();
        waiterChatService.setAcknowledged();
        // before TimeOut of five seconds
        assertFalse( waiterChatService.trySetAcknowledgementTimedOut() );
        Thread.sleep( WaiterChatService.TIME_OUT_DURATION.toMillis() );
        // After TimeOut
        assertTrue( waiterChatService.trySetAcknowledgementTimedOut() );
        assertFalse( waiterChatService.isOnline() );
        assertFalse( waiterChatService.isAcknowledgedNow() );
    }
}