package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class InstaSlot extends ParkingSlot {
    // Attributes
    @SerializedName("price")
    private double price;
    @SerializedName("max_hours")
    private int maxDurationHours = 1;
    @SerializedName("overstay_charge")
    private double overStayCharge;
    @SerializedName("booked_times")
    private List<String> bookedTimes;

    // Constructors
    public InstaSlot() {}
    public InstaSlot(String location, String managerId, boolean lotType, String locationName, boolean isAvailable, double price, int maxDurationHours, double overStayCharge) {
        super(location, managerId, lotType, locationName, isAvailable);
        this.price = price;
        this.maxDurationHours = maxDurationHours;
        this.overStayCharge = overStayCharge;
        this.bookedTimes = new ArrayList<>();
    }

    // Getters and Setters
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}

    public int getMaxDurationHours() {return maxDurationHours;}
    public void setMaxDurationHours(int maxDurationHours) {this.maxDurationHours = maxDurationHours;}

    public double getOverStayCharge() {return overStayCharge;}
    public void setOverStayCharge(double overStayCharge) {this.overStayCharge = overStayCharge;}

    public List<String> getBookedTimes() {return bookedTimes;}
    public void setBookedTimes(List<String> bookedTimes) {this.bookedTimes = bookedTimes;}

    // Overrides
    @Override
    public String toString() {
        return super.toString() + " InstaSlot{" +
                "price=" + price +
                ", maxDurationHours=" + maxDurationHours +
                ", overStayCharge=" + overStayCharge +
                ", bookedTimes=" + bookedTimes +
                '}';
    }
}
