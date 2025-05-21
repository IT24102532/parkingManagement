package lk.sliit.parkingmanagement.oopapp.dao;
/**Import  libraries**/
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.Transaction;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**Handles all database operations for transactions**/
public class TransactionDaoImpl implements TransactionDao {
    private final String transactionsPath = FileConfig.INSTANCE.getTransactionPath();
    private final Logger LOGGER = Logger.getLogger(TransactionDaoImpl.class.getName());
    /** Helper to work with JSON data**/
    JsonHelper<Transaction> jsonHelper = new JsonHelper<Transaction>(transactionsPath, Transaction.class);

    /**Find a transaction by its ID**/
    @Override
    public Transaction getById(String id) throws Exception {
        try {
            return jsonHelper.findOne(
                    t -> t.getTransactionId().equalsIgnoreCase(id)
            );
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }
    /** Get all transactions in the system**/
    @Override
    public List<Transaction> findAll() throws Exception {
        try {
            return jsonHelper.readAll();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error reading transactions", e);
            return Collections.emptyList();
        }
    }
    /**Save a new transaction**/
    @Override
    public void create(Transaction object) throws Exception {
        try {
            jsonHelper.create(object);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating transaction", e);
        }
    }
    /**Update existing transaction**/
    @Override
    public void update(Transaction object) throws Exception {

    }
    /** Delete a transaction**/
    @Override
    public void delete(int id) throws Exception {

    }
/** Find transaction using booking ID**/
    @Override
    public Transaction getByBookingId(String bookingId) {
        try {
            return jsonHelper.findOne(
                    t -> t.getBookingId().equals(bookingId)
            );
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }
}
