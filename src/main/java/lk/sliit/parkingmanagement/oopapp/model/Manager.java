package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Manager {
    // Attributes
    @SerializedName("manager_id")
    private String managerId;
    @SerializedName("commission_prec")
    private double commisionPrec;
    @SerializedName("created_at")
    private LocalDateTime createdAt;
    @SerializedName("updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Manager() {}

    public Manager(double commisionPrec) {
        this.managerId = UUID.randomUUID().toString();
        this.commisionPrec = commisionPrec;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getManagerId() {return managerId;}
    public void setManagerId(String managerId) {this.managerId = managerId;}

    public double getCommisionPrec() {return commisionPrec;}
    public void setCommisionPrec(double commisionPrec) {this.commisionPrec = commisionPrec;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}

    // Overrides
    @Override
    public String toString() {
        return "Manager{"+
                "managerId='" + managerId + '\'' +
                ", commisionPrec=" + commisionPrec +
                ", createdAt=" + createdAt + '\'' +
                ", updatedAt=" + updatedAt + '\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(managerId, manager.managerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(managerId);
    }
}
