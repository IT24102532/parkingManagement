package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.model.User;

public interface UserDao extends DAO<User>{
    User findByEmail(String email);
    boolean validatePassword(String email, String password) throws Exception;
    String getUserId(String email) throws Exception;
}
