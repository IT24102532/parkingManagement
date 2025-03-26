package lk.sliit.parkingmanagement.oopapp;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public class Transaction {
    @SerializedName("transactionId")
    private String transactionId;
    @SerializedName("bookingId")
    private String bookingId;
    @SerializedName("amount")
    private double amount;
    @SerializedName("overdueAmount")
    private double overdueAmount;
    @SerializedName("date")
    private Date date;
    @SerializedName("payStatus")
    private boolean payStatus;

    public Transaction() {}

    public Transaction(String transactionId, String bookingId, double amount, double overdueAmount, Date date, boolean payStatus) {
        this.transactionId = transactionId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.overdueAmount = overdueAmount;
        this.date = date;
        this.payStatus = payStatus;
    }

    public String getTransactionId() {return transactionId;}
    public void setTransactionId(String transactionId) {this.transactionId = transactionId;}

    public String getBookingId() {return bookingId;}
    public void setBookingId(String bookingId) {this.bookingId = bookingId;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public double getOverdueAmount() {return overdueAmount;}
    public void setOverdueAmount(double overdueAmount) {this.overdueAmount = overdueAmount;}

    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}

    public boolean isPayStatus() {return payStatus;}
    public void setPayStatus(boolean payStatus) {this.payStatus = payStatus;}

    @Override
    public String toString() {
        return "Transaction {" +
                "transactionId='" + transactionId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", amount=" + amount +
                ", overdueAmount=" + overdueAmount +
                ", date=" + date +
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
