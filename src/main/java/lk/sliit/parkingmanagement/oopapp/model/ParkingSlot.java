package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class ParkingSlot {
    @SerializedName("slotId")
    protected String slotId;
    @SerializedName("type")
    protected String lotType;
    @SerializedName("location")
    protected String location;
    @SerializedName("locationName")
    protected String locationName;
    @SerializedName("isAvailable")
    protected boolean isAvailable;
    @SerializedName("bookedDates")
    protected List<String> bookedDates;

    public ParkingSlot() {}

    public ParkingSlot(String slotId, String lotType, String location, String locationName, boolean isAvailable, List<String> bookedDates) {
        this.slotId = slotId;
        this.lotType = lotType;
        this.location = location;
        this.locationName = locationName;
        this.isAvailable = isAvailable;
        this.bookedDates = bookedDates;
    }

    public String getParkingSlotID() {return slotId;}
    public void setParkingSlotID(String parkingSlotID) {this.slotId = parkingSlotID;}

    public String getLotType() {return lotType;}
    public void setLotType(String lotType) {this.lotType = lotType;}

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    public String getLocationName() {return locationName;}
    public void setLocationName(String locationName) {this.locationName = locationName;}

    public boolean isAvailable() {return isAvailable;}
    public void setAvailable(boolean isAvailable) {this.isAvailable = isAvailable;}

    public List<String> getBookedDates() {return bookedDates;}
    public void setBookedDates(List<String> bookedDates) {this.bookedDates = bookedDates;}

    @Override
    public String toString() {
        return "ParkingSlot{" +
                "slotId='" + slotId + '\'' +
                ", lotType='" + lotType + '\'' +
                ", location='" + location + '\'' +
                ", locationName='" + locationName + '\'' +
                ", isAvailable=" + isAvailable +
                ", bookedDates=" + bookedDates +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot slot = (ParkingSlot) o;
        return Objects.equals(slotId, slot.slotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slotId);
    }
}

