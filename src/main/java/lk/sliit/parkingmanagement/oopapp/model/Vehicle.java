package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Vehicle {
    // Attributes
    @SerializedName("vehicle_id")
    private String vehicleId;
    @SerializedName("vehicle_type")
    private String vehicleType;
    @SerializedName("reg_country")
    private String regCountry;
    @SerializedName("reg_state")
    private String regState;
    @SerializedName("reg_number")
    private String regNumber;
    @SerializedName("created_at")
    private LocalDateTime createdAt;
    @SerializedName("updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Vehicle() {}
    public Vehicle(String vehicleType, String regCountry, String regState, String regNumber) {
        this.vehicleId = UUID.randomUUID().toString();
        this.vehicleType = vehicleType;
        this.regCountry = regCountry;
        this.regState = regState;
        this.regNumber = regNumber;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getVehicleId() {return vehicleId;}
    public void setVehicleId(String vehicleId) {this.vehicleId = vehicleId;}

    public String getVehicleType() {return vehicleType;}
    public void setVehicleType(String vehicleType) {this.vehicleType = vehicleType;}

    public String getRegCountry() {return regCountry;}
    public void setRegCountry(String regCountry) {this.regCountry = regCountry;}

    public String getRegState() {return regState;}
    public void setRegState(String regState) {this.regState = regState;}

    public String getRegNumber() {return regNumber;}
    public void setRegNumber(String regNumber) {this.regNumber = regNumber;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}

    // Overrides
    @Override
    public String toString() {
        return "VehicleDetails{" +
                "vehicle_id='" + vehicleId + '\'' +
                ", vehicle_type='" + vehicleType + '\'' +
                ", reg_country='" + regCountry + '\'' +
                ", reg_state='" + regState + '\'' +
                ", reg_number='" + regNumber + '\'' +
                ", created_at=" + createdAt + '\'' +
                ", updated_at=" + updatedAt + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehicleId, vehicle.vehicleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId);
    }
}
