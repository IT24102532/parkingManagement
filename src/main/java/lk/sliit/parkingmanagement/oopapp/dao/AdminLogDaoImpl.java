package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.AdminLogs;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminLogDaoImpl implements AdminLogDao {
    private final String logFile = FileConfig.INSTANCE.getLogsPath();
    private final Logger LOGGER = Logger.getLogger(AdminLogDaoImpl.class.getName());
    JsonHelper<AdminLogs> jsonHelper = new JsonHelper<>(logFile, AdminLogs.class);

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

    @Override
    public void create(AdminLogs object) throws Exception {
        try {
            jsonHelper.create(object);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void update(AdminLogs object) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }
}
