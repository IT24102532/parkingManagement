package lk.sliit.parkingmanagement.oopapp.model;

public class PaymentDetails {
    private String cardNumber;
    private String expiry;

    public PaymentDetails(String cardNumber, String expiry) {
        this.cardNumber = cardNumber;
        this.expiry = expiry;
    }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getExpiry() { return expiry; }
    public void setExpiry(String expiry) { this.expiry = expiry; }

    @Override
    public String toString() {
        return "PaymentDetails{" +
                "cardNumber='" + cardNumber + '\'' +
                ", expiry='" + expiry + '\'' +
                '}';
    }
}
