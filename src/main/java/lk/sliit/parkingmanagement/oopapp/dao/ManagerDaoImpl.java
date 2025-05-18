package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.Manager;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.utils.Log.Log;
import lk.sliit.parkingmanagement.oopapp.utils.Log.LogType;

import java.util.List;

public class ManagerDaoImpl implements ManagerDao {
    private final String file = FileConfig.INSTANCE.getManagerPath();
    private final JsonHelper<Manager> jsonHelper = new JsonHelper<>(file, Manager.class);
    @Override
    public Manager getById(String id) throws Exception {
        return null;
    }

    @Override
    public List<Manager> findAll() throws Exception {
        return jsonHelper.readAll();
    }

    @Override
    public void create(Manager object) throws Exception {
        try {
            jsonHelper.create(object);
        }
        catch (Exception e) {
            Log.type(LogType.ERROR).message(e.getMessage()).print();
        }
    }

    @Override
    public void update(Manager object) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }
}
