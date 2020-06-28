package org.thekiddos.manager;

import org.thekiddos.manager.transactions.Transaction;
import org.thekiddos.manager.transactions.source.TransactionSource;

public class Main {
    private final TransactionSource source;

    public Main( TransactionSource source ) {
        this.source = source;
    }

    public static void main( String[] args ) {
        // Main application = new Main(  );
        // application.run();
    }

    public void run() {
        Transaction transaction;
        while ( ( transaction = source.getTransaction() ) != null ) {
            transaction.execute();
        }
    }
}
