package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.model.User;

import java.util.Map;
import java.util.function.Predicate;

/** * Manages all user account database operations
 ** offers techniques for managing and authenticating users.
 */

public interface UserDao extends DAO<User>{
    /** Finds a user by their email address**/
    User findByEmail(String email);
    /** Finds a user by their email address**/
    User findById(String id);
    /**Checks if the password matches for a given email**/
    boolean validatePasswordByEmail(String email, String password) throws Exception;
    /** Checks if the password matches for a given user ID**/
    boolean validatePasswordById(String id, String password) throws Exception;
    /** Gets the user ID associated with an email address**/
    String getUserId(String email) throws Exception;
    /** Updates a user's first and last name**/
    void updateAccountDetails(String fname, String lname, String userId) throws Exception;
    /** Permanently deletes a user account**/
    void delete(String id) throws Exception;
    /**Temporarily disables a user account**/
    void ban(String id) throws Exception;
    /**Updates specific fields of a user account**/
    void partialUpdate(Predicate<User> predicate, Map<String, Object> updates) throws Exception;
}
