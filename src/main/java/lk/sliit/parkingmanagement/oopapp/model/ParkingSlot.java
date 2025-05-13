package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ParkingSlot {
    // Attributes
    @SerializedName("slot_id")
    protected String slotId;
    @SerializedName("location")
    protected String location;
    @SerializedName("manager_id")
    protected String managerId;
    @SerializedName("type")
    protected String lotType; // "insta" = Insta, "long_term" = long_term
    @SerializedName("locationName")
    protected String locationName;
    @SerializedName("slotName")
    protected String slotName;
    @SerializedName("isAvailable")
    protected boolean isAvailable;
    @SerializedName("created_at")
    protected LocalDateTime createdAt;
    @SerializedName("updated_at")
    protected LocalDateTime updatedAt;

    // Constructors
    public ParkingSlot() {}
    public ParkingSlot(String location, String managerId, String lotType, String locationName, String slotName, boolean isAvailable) {
        this.slotId = UUID.randomUUID().toString();
        this.location = location;
        this.managerId = managerId;
        this.lotType = lotType;
        this.locationName = locationName;
        this.slotName = slotName;
        this.isAvailable = isAvailable;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getSlotId() {return slotId;}
    public void setSlotId(String slotId) {this.slotId = slotId;}

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    public String getManagerId() {return managerId;}
    public void setManagerId(String managerId) {this.managerId = managerId;}

    public String getLotType() {return lotType;}
    public void setLotType(String lotType) {this.lotType = lotType;}

    public String getLocationName() {return locationName;}
    public void setLocationName(String locationName) {this.locationName = locationName;}

    public String getSlotName() {return slotName;}
    public void setSlotName(String slotName) {this.slotName = slotName;}

    public boolean isAvailable() {return isAvailable;}
    public void setAvailable(boolean available) {isAvailable = available;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}

    // Overrides
    @Override
    public String toString() {
        return "ParkingSlot{" +
                "slot_id='" + slotId + '\'' +
                ", location='" + location + '\'' +
                ", manager_id='" + managerId + '\'' +
                ", lotType=" + lotType +
                ", locationName='" + locationName + '\'' +
                ", slotName='" + slotName + '\'' +
                ", isAvailable=" + isAvailable +
                ", createdAt=" + createdAt + '\'' +
                ", updatedAt=" + updatedAt + '\'' +
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

