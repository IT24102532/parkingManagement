package lk.sliit.parkingmanagement.oopapp.FilleHander;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jdk.jfr.Frequency;
import lk.sliit.parkingmanagement.oopapp.PaymentDetails;
import lk.sliit.parkingmanagement.oopapp.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class CardDetailsFileHandler {

    private final String  FILE_PATH = "cardDetails.txt";


    public void saveCardDetailsToFile (PaymentDetails details , User user) throws IOException {




        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))){
            String cardDetails =  details.toString();


            bw.write(cardDetails);
            bw.newLine();

        } catch (IOException e) {
            throw new IOException("Error cannot write to a file");
        }


    }

}
