package lk.sliit.parkingmanagement.oopapp;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User{
    public Admin(String username, String email, String userId, String hashedPassword) {
        super(username, email, userId, hashedPassword, "admin", new ArrayList<>());
    }

    @Override
    public String toString() {
        return super.toString() + " AdminUser{}";
    }
}
