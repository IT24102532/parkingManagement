package lk.sliit.parkingmanagement.oopapp.dto;

import com.google.gson.annotations.SerializedName;

// Data Transfer Object (DTO) for Vehicle information
// Used to transfer vehicle data between application layers

public class VehicleDto {

    @SerializedName("user_uuid")
    private String userId;

    @SerializedName("vehicle_id")
    private String vehicleId;

    @SerializedName("vehicle_type")
    private String vehicleType;










































    
    @SerializedName("reg_number")
    private String regNumber;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }
}
