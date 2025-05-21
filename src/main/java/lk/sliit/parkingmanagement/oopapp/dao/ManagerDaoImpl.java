package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.Manager;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.utils.Log.Log;
import lk.sliit.parkingmanagement.oopapp.utils.Log.LogType;

import java.util.List;

/** * ManagerDao interface implementation, which uses JSON file storage to enable data access
 * * operations for Manager entities.
 * * This class persists Manager objects to a JSON file and manages all CRUD (Create, Read, Update, Delete) operations
  **/

public class ManagerDaoImpl implements ManagerDao {
    private final String file = FileConfig.INSTANCE.getManagerPath();
    private final JsonHelper<Manager> jsonHelper = new JsonHelper<>(file, Manager.class);

    /** * Obtains a manager by ID (not used at this time).
     * param id The manager's ID to retrieve
     ** return Since this method is not implemented, it always returns null.
     * * @throws Exception If an error arises (which is not anticipated at this time)
     **/
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

    /**
     * Deletes a manager record by ID (currently not implemented).
     *
     * param id The ID of the manager to delete
     * throws Exception If any error occurs (though none expected currently)
     */

    @Override
    public void delete(int id) throws Exception {

    }
}
