package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.model.Transaction;
/**
 * Data Access Object interface for Transaction operations
 * Extends generic DAO interface with Transaction type
 */

public interface TransactionDao extends DAO<Transaction> {
    Transaction getByBookingId(String bookingId);
}
/**
 * Finds a transaction by its booking ID
 * param bookingId The unique booking identifier
 * return Transaction object if found, null otherwise
 */