package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.AdminLogs;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Handles saving and loading admin activity logs
public class AdminLogDaoImpl implements AdminLogDao {
    // File where all admin logs are stored
    private final String logFile = FileConfig.INSTANCE.getLogsPath();
    // For recording errors
    private final Logger LOGGER = Logger.getLogger(AdminLogDaoImpl.class.getName());
    //Helper to work with JSON files
    JsonHelper<AdminLogs> jsonHelper = new JsonHelper<>(logFile, AdminLogs.class);

    // Finds one log by its ID
    @Override
    public AdminLogs getById(String id) throws Exception {
        try {
           return jsonHelper.findOne(
                   l -> l.getLogId().equalsIgnoreCase(id)
           );
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    // Gets all admin logs
    @Override
    public List<AdminLogs> findAll() throws Exception {
        try {
            return jsonHelper.readAll();
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return List.of();
    }
    // Saves a new admin log
    @Override
    public void create(AdminLogs object) throws Exception {
        try {
            jsonHelper.create(object);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }


    /**
     * Modifies an admin log entry that already exists in the storage. (Not yet implemented.)

      * param object The AdminLogs object that needs to be modified * @throws Exception In the event that an error arises during the operation,
     */

    // Updates a log
    @Override
    public void update(AdminLogs object) throws Exception {

    }

    /**
    *   Removes an admin log entry based on its ID. (Not yet implemented.)
     * param id The admin log ID that needs to be removed
     * * throwsException In the event that an error arises during the operation,
     */

    @Override
    public void delete(int id) throws Exception {

    }
}
