package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.Transaction;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionDaoImpl implements TransactionDao {
    private final String transactionsPath = FileConfig.INSTANCE.getTransactionPath();
    private final Logger LOGGER = Logger.getLogger(TransactionDaoImpl.class.getName());
    JsonHelper<Transaction> jsonHelper = new JsonHelper<Transaction>(transactionsPath, Transaction.class);

    @Override
    public Transaction getById(String id) throws Exception {
        return null;
    }

    @Override
    public List<Transaction> findAll() throws Exception {
        try {
            return jsonHelper.readAll();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error reading transactions", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void create(Transaction object) throws Exception {
        try {
            jsonHelper.create(object);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating transaction", e);
        }
    }

    @Override
    public void update(Transaction object) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }
}
