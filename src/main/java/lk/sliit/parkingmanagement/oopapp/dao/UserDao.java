package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.model.User;

import java.util.Map;
import java.util.function.Predicate;

public interface UserDao extends DAO<User>{
    User findByEmail(String email);
    User findById(String id);
    boolean validatePasswordByEmail(String email, String password) throws Exception;
    boolean validatePasswordById(String id, String password) throws Exception;
    String getUserId(String email) throws Exception;
    void updateAccountDetails(String fname, String lname, String userId) throws Exception;
    void delete(String id) throws Exception;
    void ban(String id) throws Exception;
    void partialUpdate(Predicate<User> predicate, Map<String, Object> updates) throws Exception;
}
