package lk.sliit.parkingmanagement.oopapp.FilleHander;

import lk.sliit.parkingmanagement.oopapp.PaymentDetails;
import lk.sliit.parkingmanagement.oopapp.User;
import lk.sliit.parkingmanagement.oopapp.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;

import java.io.IOException;
import java.util.List;

public class CardDetailsFileHandler {

    private final JsonHelper<PaymentDetails> jsonHelper;

    public CardDetailsFileHandler() {
        this.jsonHelper = new JsonHelper<>(FileConfig.INSTANCE.getTransactionPath(), PaymentDetails.class);
    }

    public void saveCardDetailsToFile(PaymentDetails details, User user) {
        List<PaymentDetails> existing = jsonHelper.readAll();
        existing.add(details);
        jsonHelper.writeAll(existing);
    }
}
