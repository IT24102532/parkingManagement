package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

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

    // Constructors
    public Vehicle() {}
    public Vehicle(String vehicleId, String vehicleType, String regCountry, String regState, String regNumber) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.regCountry = regCountry;
        this.regState = regState;
        this.regNumber = regNumber;
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

    // Overrides
    @Override
    public String toString() {
        return "VehicleDetails{" +
                "vehicle_id='" + vehicleId + '\'' +
                ", vehicle_type='" + vehicleType + '\'' +
                ", reg_country='" + regCountry + '\'' +
                ", reg_state='" + regState + '\'' +
                ", reg_number='" + regNumber + '\'' +
                '}';
    }
}
