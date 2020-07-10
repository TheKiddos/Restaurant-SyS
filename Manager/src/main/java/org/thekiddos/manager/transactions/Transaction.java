package org.thekiddos.manager.transactions;

/**
 * An implementation of the command pattern
 * All Application functionality is implemented using transactions
 * this simplify dealing with the database, dealing with immutability, creating objects
 * and gives a unified working way
 * you should not creates or modify objects outside of transactions.
 */
public interface Transaction {
    void execute();
}
