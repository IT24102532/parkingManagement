package lk.sliit.parkingmanagement.oopapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class ParkingSlot {
    @SerializedName("parkingSlotId")
    protected String parkingSlotID;
    @SerializedName("lotType")
    protected String lotType;
    @SerializedName("location")
    protected String location;
    @SerializedName("isAvailable")
    protected boolean isAvailable;
    @SerializedName("bookedDates")
    protected List<String> bookedDates;

    public ParkingSlot() {}

    public ParkingSlot(String parkingSlotID, String lotType, String location, boolean isAvailable, List<String> bookedDates) {
        this.parkingSlotID = parkingSlotID;
        this.lotType = lotType;
        this.location = location;
        this.isAvailable = isAvailable;
        this.bookedDates = bookedDates;
    }

    public String getParkingSlotID() {return parkingSlotID;}
    public void setParkingSlotID(String parkingSlotID) {this.parkingSlotID = parkingSlotID;}

    public String getLotType() {return lotType;}
    public void setLotType(String lotType) {this.lotType = lotType;}

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    public boolean isAvailable() {return isAvailable;}
    public void setAvailable(boolean isAvailable) {this.isAvailable = isAvailable;}

    public List<String> getBookedDates() {return bookedDates;}
    public void setBookedDates(List<String> bookedDates) {}

    @Override
    public String toString() {
        return "ParkingLot{" +
                "lotId='" + parkingSlotID + '\'' +
                ", lotType='" + lotType + '\'' +
                ", location='" + location + '\'' +
                ", isAvailable=" + isAvailable +
                ", bookedDates=" + bookedDates +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot slot = (ParkingSlot) o;
        return Objects.equals(parkingSlotID, slot.parkingSlotID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkingSlotID);
    }
}

