package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.model.Transaction;

public interface TransactionDao extends DAO<Transaction> {
    Transaction getByBookingId(String bookingId);
}
