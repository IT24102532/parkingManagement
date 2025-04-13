package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Customer extends User {
    @SerializedName("carType")
    private String carType;
    @SerializedName("licensePlate")
    private String licensePlate;
    @SerializedName("paymentDetails")
    private PaymentDetails paymentDetails;

    public Customer(String username, String email, String userId, String hashedPassword, String carType, String licensePlate, PaymentDetails paymentDetails) {
        super(username, email, userId, hashedPassword, "user", new ArrayList<>());
        this.carType = carType;
        this.licensePlate = licensePlate;
        this.paymentDetails = paymentDetails;
    }

    public String getCarType() { return carType; }
    public void setCarType(String carType) { this.carType = carType; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public PaymentDetails getPaymentDetails() { return paymentDetails; }
    public void setPaymentDetails(PaymentDetails paymentDetails) { this.paymentDetails = paymentDetails; }

    @Override
    public String toString() {
        return super.toString() + " Customer{" +
                "carType='" + carType + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", paymentDetails=" + paymentDetails +
                '}';
    }
}
