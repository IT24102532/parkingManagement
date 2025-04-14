package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LongTermSlot extends ParkingSlot {
    // Attributes
    @SerializedName("price")
    private double price;
    @SerializedName("overstay_charge")
    private double overstayCharge;
    @SerializedName("bookedDates")
    private List<String> bookedDates;

    // Constructors
    public LongTermSlot() {}
    public LongTermSlot(String slotId, String location, String managerId, boolean lotType, String locationName, boolean isAvailable, double price, double overstayCharge) {
        super(slotId, location, managerId, lotType, locationName, isAvailable);
        this.price = price;
        this.overstayCharge = overstayCharge;
        this.bookedDates = new ArrayList<>();
    }

    // Getters and Setters
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}

    public double getOverstayCharge() {return overstayCharge;}
    public void setOverstayCharge(double overstayCharge) {this.overstayCharge = overstayCharge;}

    public List<String> getBookedDates() {return bookedDates;}
    public void setBookedDates(List<String> bookedDates) {this.bookedDates = bookedDates;}

    // Overrides
    @Override
    public String toString() {
        return super.toString() + " LongTermSlot{" +
                "price=" + price +
                ", overstayCharge=" + overstayCharge +
                ", bookedDates=" + bookedDates +
                '}';
    }
}
