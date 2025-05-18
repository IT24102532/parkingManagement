package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class User {
    // Attributes
    @SerializedName("user_uuid")
    protected String user_uuid;
    // TIP: When generating username, get the first name and last name
    // When passing attributes, send it as "f_name+l_name"
    @SerializedName("f_name")
    protected String f_name;
    @SerializedName("l_name")
    protected String l_name;
    @SerializedName("email")
    protected String email;
    @SerializedName("hashedPassword")
    protected String hashedPassword;
    @SerializedName("user_type")
    protected String user_type;
    // TODO: Figure out if booking needs to be put in USER considering the way we have a booking class
    @SerializedName("bookings")
    protected List<String> bookings;
    // Logging
    @SerializedName("created_at")
    protected LocalDateTime created_at;
    @SerializedName("updated_at")
    protected LocalDateTime updated_at;
    @SerializedName("banned")
    protected Boolean banned;

    // Constructors
    public User() {}
    public User(String user_uuid, String f_name, String l_name, String email, String hashedPassword, String user_type, List<String> bookings) {
        this.user_uuid = user_uuid;
        this.f_name = f_name;
        this.l_name = l_name;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.user_type = user_type;
        this.bookings = bookings;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
        this.banned = false;
    }

    //Getters and setters
    public String getFirstName() {return f_name;}
    public void setFirstName(String username) {this.f_name = username;}

    public String getUserId() {return user_uuid;}
    public void setUserId(String userId) {this.user_uuid = userId;}

    public String getLastName() {return l_name;}
    public void setLastName(String lastName) {this.l_name = lastName;}

    public String getHashedPassword() {return hashedPassword;}
    public void setHashedPassword(String hashedPassword) {this.hashedPassword = hashedPassword;}

    public String getUserType() {return user_type;}
    public void setUserType(String user_type) {this.user_type = user_type;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public List<String> getBookings() { return bookings;}
    public void setBookings(List<String> bookings) {this.bookings = bookings;}

    public LocalDateTime getCreatedAt() {return created_at;}
    public void setCreatedAt(LocalDateTime created_at) {this.created_at = created_at;}

    public LocalDateTime getUpdatedAt() {return updated_at;}
    public void setUpdatedAt(LocalDateTime updated_at) {this.updated_at = updated_at;}

    public Boolean getBanned() {return banned;}
    public void setBanned(Boolean banned) {this.banned = banned;}

    public String getUsername() {return this.f_name + " " + this.l_name;}

    // Overrides
    @Override
    public String toString() {
        return "User{" +
                "user_uuid='" + user_uuid + '\'' +
                ", user_type='" + user_type + '\'' +
                ", f_name='" + f_name + '\'' +
                ", l_name='" + l_name + '\'' +
                ", email='" + email + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", banned=" + banned +
                ", bookings=" + bookings +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(user_uuid, user.user_uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_uuid);
    }

}
