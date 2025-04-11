package lk.sliit.parkingmanagement.oopapp.FilleHander;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lk.sliit.parkingmanagement.oopapp.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserFileHandler{

    private static  final String FILEPATH = "customerDetails.txt";
    private  final Gson gson;
    public UserFileHandler() {
        gson= new GsonBuilder().setPrettyPrinting().create();
    }

    public void saveCustomer(Customer customer){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH,true))){
            String customerJson = gson.toJson(customer);
            writer.write(customerJson);
            writer.newLine();

        }catch (IOException e){
            e.printStackTrace();

        }
    }

    public List<Customer> loadAllCustomers(){
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Customer customer = gson.fromJson(line, Customer.class);
                customers.add(customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }
    public Customer getCustomerByEmail(String email) {
        List<Customer> customers = loadAllCustomers();
        for (Customer customer : customers) {
            if (customer.getEmail().equalsIgnoreCase(email)) {
                return customer;
            }
        }
        return null;
    }

    public boolean isEmailTaken(String email) {
        return getCustomerByEmail(email) != null;
    }




}