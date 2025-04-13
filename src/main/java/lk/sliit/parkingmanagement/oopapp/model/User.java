package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class User {
    @SerializedName("username")
    protected String username;
    @SerializedName("email")
    protected String email;
    @SerializedName("userId")
    protected String userId;
    @SerializedName("hashedPassword")
    protected String hashedPassword;
    @SerializedName("userType")
    protected String userType;
    @SerializedName("bookings")
    protected List<String> bookings;

    public User() {}

    public User(String username, String email, String userId, String hashedPassword, String userType, List<String> bookings) {
        this.username = username;
        this.email = email;
        this.userId = userId;
        this.hashedPassword = hashedPassword;
        this.userType = userType;
        this.bookings = bookings;
    }


    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}

    public String getHashedPassword() {return hashedPassword;}
    public void setHashedPassword(String hashedPassword) {this.hashedPassword = hashedPassword;}

    public String getUserType() {return userType;}
    public void setUserType(String userType) {this.userType = userType;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public List<String> getBookings() { return bookings;}
    public void setBookings(List<String> bookings) {this.bookings = bookings;}

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", name='" + username + '\'' +
                ", email='" + email + '\'' +
                ", bookings=" + bookings +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
