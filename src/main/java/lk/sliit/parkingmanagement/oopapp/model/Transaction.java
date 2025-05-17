package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Transaction {
    // Attributes
    @SerializedName("transaction_id")
    private String transactionId;
    @SerializedName("booking_id")
    private String bookingId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("amount")
    private double amount;
    @SerializedName("overdueAmount")
    private double overdueAmount;
    @SerializedName("created_at")
    private LocalDateTime createdAt;
    @SerializedName("payStatus")
    private boolean payStatus;

    // Constructors
    public Transaction() {}

    public Transaction(String bookingId, String userId, double amount, double overdueAmount, boolean payStatus) {
        this.transactionId = UUID.randomUUID().toString();
        this.bookingId = bookingId;
        this.userId = userId;
        this.amount = amount;
        this.overdueAmount = overdueAmount;
        this.payStatus = payStatus;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getTransactionId() {return transactionId;}
    public void setTransactionId(String transactionId) {this.transactionId = transactionId;}

    public String getBookingId() {return bookingId;}
    public void setBookingId(String bookingId) {this.bookingId = bookingId;}

    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public double getOverdueAmount() {return overdueAmount;}
    public void setOverdueAmount(double overdueAmount) {this.overdueAmount = overdueAmount;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public boolean isPayStatus() {return payStatus;}
    public void setPayStatus(boolean payStatus) {this.payStatus = payStatus;}

    // Overrides
    @Override
    public String toString() {
        return "Transaction {" +
                "transactionId='" + transactionId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", userId='" + userId + '\'' +
                ", amount=" + amount +
                ", overdueAmount=" + overdueAmount +
                ", createdAt=" + createdAt + '\'' +
                ", payStatus=" + payStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        return Objects.equals(transactionId, transaction.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }
}
