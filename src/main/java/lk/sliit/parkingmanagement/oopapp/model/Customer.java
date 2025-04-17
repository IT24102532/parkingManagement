package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    // Attributes
    @SerializedName("VehicleDetails")
    private Vehicle vehicle;
    @SerializedName("PaymentDetails")
    private PaymentDetails paymentDetails;

    // Constructors
    public Customer() {}
    public Customer(String user_uuid, String f_name, String l_name, String email, String hashedPassword, String user_type, List<String> bookings, Vehicle vehicle, PaymentDetails paymentDetails) {
        super(user_uuid, f_name, l_name, email, hashedPassword, user_type, bookings);
        this.vehicle = vehicle;
        this.paymentDetails = paymentDetails;
    }

    // Getters and Setters
    public Vehicle getVehicle() {return vehicle;}
    public void setVehicle(Vehicle vehicle) {this.vehicle = vehicle;}

    public PaymentDetails getPaymentDetails() {return paymentDetails;}
    public void setPaymentDetails(PaymentDetails paymentDetails) {this.paymentDetails = paymentDetails;}

    // Overrides
    @Override
    public String toString() {
        return super.toString() + " Customer{" +
                "VehicleDetails=" + vehicle  +
                ", PaymentDetails=" + paymentDetails +
                '}';
    }
}
