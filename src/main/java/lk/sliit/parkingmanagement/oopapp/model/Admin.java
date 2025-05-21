package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

public class Admin extends User {
    // Attributes
    @SerializedName("sec_clearance")
    private int secClearance;
    @SerializedName("role")
    private String role;

    // Constructors
    public Admin() {}
    public Admin(String f_name, String l_name, String email, String hashedPassword, String user_type, List<String> bookings, int secClearance, String role) {
        super(UUID.randomUUID().toString(), f_name, l_name, email, hashedPassword, user_type, bookings);
        this.secClearance = secClearance;
        this.role = role;
    }

    // Getters and Setters
    public int getSecClearance() {return secClearance;}
    public void setSecClearance(int secClearance) {this.secClearance = secClearance;}

    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}

    // Overrides
    @Override
    public String toString() {
        return super.toString() + " AdminUser{" +
                "secClearance='" + secClearance + '\'' +
                ", role='" + role + '\'' +
                "}";
    }
}
