package lk.sliit.parkingmanagement.oopapp.dto;

import com.google.gson.annotations.SerializedName;
import lk.sliit.parkingmanagement.oopapp.model.PaymentDetails;
import lk.sliit.parkingmanagement.oopapp.model.Vehicle;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {
    @SerializedName("user_uuid")
    private String user_uuid;
    @SerializedName("f_name")
    private String f_name;
    @SerializedName("l_name")
    private String l_name;
    @SerializedName("email")
    private String email;
    @SerializedName("user_type")
    private String user_type;
    @SerializedName("bookings")
    private List<String> bookings;
    @SerializedName("created_at")
    private LocalDateTime created_at;
    @SerializedName("updated_at")
    private LocalDateTime updated_at;
    @SerializedName("VehicleDetails")
    private Vehicle vehicle;
    @SerializedName("PaymentDetails")
    private PaymentDetails paymentDetails;

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public List<String> getBookings() {
        return bookings;
    }

    public void setBookings(List<String> bookings) {
        this.bookings = bookings;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}
