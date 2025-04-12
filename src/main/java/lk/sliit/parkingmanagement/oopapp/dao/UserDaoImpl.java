package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.User;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.utils.PasswordHasher;

import java.util.List;
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
    public boolean validatePassword(String email, String password) throws Exception {
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
    public User getById(int id) throws Exception {
        return null;
    }

    @Override
    public List<User> findAll() throws Exception {
        return List.of();
    }

    @Override
    public void create(User object) throws Exception {

    }

    @Override
    public void update(User object) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }
}
