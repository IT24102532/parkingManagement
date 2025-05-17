package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.User;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.utils.PasswordHasher;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getName());
    private final String filePath = FileConfig.INSTANCE.getUsersPath();
    JsonHelper<User> userJsonHelper = new JsonHelper<>(filePath, User.class);

    @Override
    public User findByEmail(String email) {
       try {
           return userJsonHelper.findOne(
                   u -> u.getEmail().equalsIgnoreCase(email)
           );
       }
       catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error Findng User by Email" + email, e);
            return null;
       }
    }

    @Override
    public boolean validatePasswordByEmail(String email, String password) throws Exception {
        try {
            User user = this.findByEmail(email);
            if (user != null) {
                return PasswordHasher.verifyPassword(password, user.getHashedPassword());
            }
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error Findng User by Email" + email, e);
        }
        return false;
    }

    @Override
    public boolean validatePasswordById(String id, String password) throws Exception {
        try {
            User user = userJsonHelper.findOne(
                    u -> u.getUserId().equalsIgnoreCase(id)
            );
            if (user != null) {
                return PasswordHasher.verifyPassword(password, user.getHashedPassword());
            }
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error Findng User by Id" + id, e);
        }
        return false;
    }

    @Override
    public String getUserId(String email) throws Exception {
        try {
            User user = userJsonHelper.findOne(
                    u -> u.getUserId().equalsIgnoreCase(email)
            );
            if (user != null) {
                return user.getUserId();
            }
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error Findng User Id" + email, e);
        }
        return null;
    }

    @Override
    public void updateAccountDetails(String fname, String lname, String userId) throws Exception {
        try {
            Map<String, Object> updates = new HashMap<>();
            updates.put("f_name", fname);
            updates.put("l_name", lname);
            userJsonHelper.partialUpdate(user -> user.getUserId().equals(userId), updates);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error Findng User by Id" + userId, e);
        }
    }

    @Override
    public User getById(String id) throws Exception {
        try {
            return userJsonHelper.findOne(u -> u.getUserId().equals(id));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding User by ID: " + id, e);
            return null;
        }
    }

    @Override
    public List<User> findAll() throws Exception {
        try {
            return userJsonHelper.readAll();
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding all Users", e);
        }
        return new ArrayList<>();
    }

    @Override
    public void create(User object) throws Exception {
        try{
            if( object.getUserId() == null || object.getUserId().isEmpty()){
                object.setUserId(UUID.randomUUID().toString());
                userJsonHelper.create(object);
            }

        }catch ( Exception e){
            LOGGER.log(Level.SEVERE, "Error saving user", e);
            throw  new Exception("Failed to save user");
        }
        userJsonHelper.create(object);
    }

    @Override
    public void update(User object) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public void delete(String id) throws Exception {
        try {
            userJsonHelper.delete(u -> u.getUserId().equals(id));
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting user", e);
        }

    }
}
