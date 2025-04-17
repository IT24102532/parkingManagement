package lk.sliit.parkingmanagement.oopapp.FilleHander;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.json.Json;
import lk.sliit.parkingmanagement.oopapp.model.Customer;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserFileHandler{

    private static  final String FILEPATH = FileConfig.INSTANCE.getUsersPath();
    private final JsonHelper<Customer> jsonHelper= new JsonHelper<>(FILEPATH, Customer.class);

    public void saveCustomer(Customer customer){
        File file = new File(FILEPATH);

        // Ensure file exists and is properly initialized as a JSON array
        if (!file.exists() || file.length() == 0) {
            try (Writer writer = new FileWriter(file)) {
                writer.write("[]"); // initialize as empty JSON array
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        List<Customer> customers = jsonHelper.readAll();
        customers.add(customer);
        jsonHelper.writeAll(customers);
    }





}