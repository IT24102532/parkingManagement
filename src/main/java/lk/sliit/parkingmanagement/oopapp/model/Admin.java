package lk.sliit.parkingmanagement.oopapp.model;

import java.util.ArrayList;

public class Admin extends User {
    public Admin(String username, String email, String userId, String hashedPassword) {
        super(username, email, userId, hashedPassword, "admin", new ArrayList<>());
    }

    @Override
    public String toString() {
        return super.toString() + " AdminUser{}";
    }
}
