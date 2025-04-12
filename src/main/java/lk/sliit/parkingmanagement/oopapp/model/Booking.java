package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public class Booking {
    @SerializedName("bookingId")
    private String bookingId;
    @SerializedName("slotId")
    private String slotId;
    @SerializedName("startDate")
    private Date startDate;
    @SerializedName("endDate")
    private Date endDate;
    @SerializedName("checkInTime")
    private Date checkInTime;
    @SerializedName("checkoutTime")
    private Date checkOutTime;
    @SerializedName("isOccupied")
    private boolean isOccupied;
    @SerializedName("isOverStayed")
    private boolean isOverStayed;
    @SerializedName("overstayStartTime")
    private Date overstayStartTime;
    @SerializedName("overstayDurationHours")
    private double overstayDurationHours;
    @SerializedName("overstayCharge")
    private double overstayCharge;
    @SerializedName("totalPrice")
    private double totalPrice;
    @SerializedName("promoCode")
    private String promoCode;

    public Booking() {}

    public Booking(String bookingId, String slotId, Date startDate, Date endDate, Date checkInTime, Date checkOutTime, boolean isOccupied, boolean isOverStayed, Date overstayStartTime, double overstayDurationHours, double overstayCharge, double totalPrice, String promoCode) {
        this.bookingId = bookingId;
        this.slotId = slotId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.isOccupied = isOccupied;
        this.isOverStayed = isOverStayed;
        this.overstayStartTime = overstayStartTime;
        this.overstayDurationHours = overstayDurationHours;
        this.overstayCharge = overstayCharge;
        this.totalPrice = totalPrice;
        this.promoCode = promoCode;
    }

    public String getBookingId() {return bookingId;}
    public void setBookingId(String bookingId) {this.bookingId = bookingId;}

    public String getSlotId() {return slotId;}
    public void setSlotId(String slotId) {this.slotId = slotId;}

    public Date getStartDate() {return startDate;}
    public void setStartDate(Date startDate) {this.startDate = startDate;}

    public Date getEndDate() {return endDate;}
    public void setEndDate(Date endDate) {this.endDate = endDate;}

    public Date getCheckInTime() {return checkInTime;}
    public void setCheckInTime(Date checkInTime) {this.checkInTime = checkInTime;}

    public Date getCheckOutTime() {return checkOutTime;}
    public void setCheckOutTime(Date checkOutTime) {this.checkOutTime = checkOutTime;}

    public boolean isOccupied() {return isOccupied;}
    public void setIsOccupied(boolean isOccupied) {this.isOccupied = isOccupied;}

    public boolean isOverStayed() {return isOverStayed;}
    public void setIsOverStayed(boolean isOverStayed) {this.isOverStayed = isOverStayed;}

    public Date getOverstayStartTime() {return overstayStartTime;}
    public void setOverstayStratTime(Date overstayStartTime) {this.overstayStartTime = overstayStartTime;}

    public double getOverstayDurationHours() {return overstayDurationHours;}
    public void setOverstayDurationHours(double overstayDurationHours) {this.overstayDurationHours = overstayDurationHours;}

    public double getOverstayCharge() {return overstayCharge;}
    public void setOverstayCharge(double overstayCharge) {this.overstayCharge = overstayCharge;}

    public double getTotalPrice() {return totalPrice;}
    public void setTotalPrice(double totalPrice) {this.totalPrice = totalPrice;}

    public String getPromoCode() {return promoCode;}
    public void setPromoCode(String promoCode) {this.promoCode = promoCode;}

    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", slotId='" + slotId + '\'' +
                ", startDateTime='" + startDate + '\'' +
                ", endDateTime='" + endDate + '\'' +
                ", checkInTime='" + checkInTime + '\'' +
                ", checkOutTime='" + checkOutTime + '\'' +
                ", isOccupied=" + isOccupied +
                ", isOverStayed=" + isOverStayed +
                ", overstayStartTime='" + overstayStartTime + '\'' +
                ", overstayDurationHours=" + overstayDurationHours +
                ", overstayCharge=" + overstayCharge +
                ", totalPrice=" + totalPrice +
                ", promoCode='" + promoCode + '\'' +
                '}';
    }

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

