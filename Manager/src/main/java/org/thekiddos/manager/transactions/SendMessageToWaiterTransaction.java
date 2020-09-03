package org.thekiddos.manager.transactions;

public class SendMessageToWaiterTransaction extends SendMessageTransaction {

    public SendMessageToWaiterTransaction( String contents ) {
        super( contents );
    }

    @Override
    String getSender() {
        return "Manager";
    }

    @Override
    String getReceiver() {
        return "Waiter";
    }
}
