package org.thekiddos.manager.transactions.source;

import org.thekiddos.manager.transactions.Transaction;

public interface TransactionSource {
    Transaction getTransaction();
}
