package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class PaymentDetails {
    // Attributes
    @SerializedName("card_id")
    private String cardId;
    @SerializedName("holder_name")
    private String holderName;
    @SerializedName("expiry_date")
    private String expiryDate;
    @SerializedName("card_type")
    private String cardType;
    @SerializedName("card_number")
    private String cardNumber;
    @SerializedName("created_at")
    private LocalDateTime createdAt;
    @SerializedName("updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public PaymentDetails() {}
    public PaymentDetails(String holderName, String expiryDate, String cardType, String cardNumber) {
        this.cardId = UUID.randomUUID().toString();
        this.holderName = holderName;
        this.expiryDate = expiryDate;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getCardId() {return cardId;}
    public void setCardId(String cardId) {this.cardId = cardId;}

    public String getHolderName() {return holderName;}
    public void setHolderName(String holderName) {this.holderName = holderName;}

    public String getExpiryDate() {return expiryDate;}
    public void setExpiryDate(String expiryDate) {this.expiryDate = expiryDate;}

    public String getCardType() {return cardType;}
    public void setCardType(String cardType) {this.cardType = cardType;}

    public String getCardNumber() {return cardNumber;}
    public void setCardNumber(String cardNumber) {this.cardNumber = cardNumber;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}

    // Overrides
    @Override
    public String toString() {
        return "PaymentDetails{" +
                "card_id='" + cardId + '\'' +
                ", holder_name='" + holderName + '\'' +
                ", expiry_date='" + expiryDate + '\'' +
                ", card_type='" + cardType + '\'' +
                ", card_number=" + cardNumber + '\'' +
                ", created_at=" + createdAt +
                ", updated_at=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDetails payment = (PaymentDetails) o;
        return Objects.equals(cardId, payment.cardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId);
    }
}
