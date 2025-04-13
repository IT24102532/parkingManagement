package lk.sliit.parkingmanagement.oopapp;

public class PaymentDetails {
    private String cardNumber;
    private String expiry;
    private String cardHolderName;

    private String cvv ;

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public PaymentDetails(String cardNumber, String expiry, String cardHolderName,String cvv) {
        this.cvv= cvv;
        this.cardNumber = cardNumber;
        this.expiry = expiry;
        this.cardHolderName= cardHolderName;
        this.cvv=cvv;
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
                ", cardHolderName='" + cardHolderName +
                '}';
    }
}
