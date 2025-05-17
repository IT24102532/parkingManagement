package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.model.User;

public interface UserDao extends DAO<User>{
    User findByEmail(String email);
    boolean validatePasswordByEmail(String email, String password) throws Exception;
    boolean validatePasswordById(String id, String password) throws Exception;
    String getUserId(String email) throws Exception;
    void updateAccountDetails(String fname, String lname, String userId) throws Exception;
    void delete(String id) throws Exception;
}
