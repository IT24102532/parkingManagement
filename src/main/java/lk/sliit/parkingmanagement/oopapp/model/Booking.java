package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Booking {
    // Attributes
    @SerializedName("booking_id")
    private String bookingId;
    @SerializedName("slot_id")
    private String slotId;
    @SerializedName("startDateTime")
    private LocalDateTime startDateTime;
    @SerializedName("checkInTime")
    private LocalTime checkInTime;
    @SerializedName("checkoutTime")
    private LocalDateTime checkOutTime;
    @SerializedName("timeOut")
    private LocalDateTime timeOut;
    @SerializedName("isOccupied")
    private boolean isOccupied;
    @SerializedName("isOverStayed")
    private boolean isOverStayed;
    @SerializedName("created_at")
    private LocalDateTime createdAt;
    @SerializedName("updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Booking() {}
    public Booking(String slotId, boolean isOccupied, boolean isOverStayed, LocalDateTime startDateTime) {
        this.bookingId = UUID.randomUUID().toString();
        this.slotId = slotId;
        this.isOccupied = isOccupied;
        this.isOverStayed = isOverStayed;
        this.startDateTime = startDateTime;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getBookingId() {return bookingId;}
    public void setBookingId(String bookingId) {this.bookingId = bookingId;}

    public String getSlotId() {return slotId;}
    public void setSlotId(String slotId) {this.slotId = slotId;}

    public boolean getOccupied() {return isOccupied;}
    public void setOccupied(boolean isOccupied) {this.isOccupied = isOccupied;}

    public boolean getOverStayed() {return isOverStayed;}
    public void setOverStayed(boolean isOverStayed) {this.isOverStayed = isOverStayed;}

    public LocalDateTime getStartDateTime() {return startDateTime;}
    public void setStartDateTime(LocalDateTime startDateTime) {this.startDateTime = startDateTime;}

    public LocalTime getCheckInTime() {return checkInTime;}
    public void setCheckInTime(LocalTime checkInTime) {this.checkInTime = checkInTime;}

    public LocalDateTime getCheckOutTime() {return checkOutTime;}
    public void setCheckOutTime(LocalDateTime checkOutTime) {this.checkOutTime = checkOutTime;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}

    public LocalDateTime getTimeOut() {return timeOut;}
    public void setTimeOut(LocalDateTime timeOut) {this.timeOut = timeOut;}


    // Methods
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", slotId='" + slotId + '\'' +
                ", startDateTime=" + startDateTime + '\'' +
                ", checkInTime=" + checkInTime + '\'' +
                ", checkInTime=" + checkInTime + '\'' +
                ", checkOutTime=" + checkOutTime + '\'' +
                ", isOccupied=" + isOccupied +
                ", isOverStayed=" + isOverStayed +
                ", createdAt=" + createdAt + '\'' +
                ", updatedAt=" + updatedAt + '\'' +
                '}';
    }

    // Overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(bookingId, booking.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }

}

