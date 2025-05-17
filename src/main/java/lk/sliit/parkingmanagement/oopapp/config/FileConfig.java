package lk.sliit.parkingmanagement.oopapp.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.File;

public enum FileConfig {
    INSTANCE;

    private String usersPath;
    private String transactionPath;
    private String bookingsPath;
    private String slotPath;
    private String managerPath;

    FileConfig() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {throw new RuntimeException("config.properties not found");}
            prop.load(input);

            usersPath = prop.getProperty("user.file.path");
            transactionPath = prop.getProperty("transaction.file.path");
            bookingsPath = prop.getProperty("booking.file.path");
            slotPath = prop.getProperty("slots.file.path");
            managerPath = prop.getProperty("manager.file.path");

            validatePaths();

            ensureFileExists(usersPath);
            ensureFileExists(transactionPath);
            ensureFileExists(bookingsPath);
            ensureFileExists(slotPath);
            ensureFileExists(managerPath);

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void validatePaths() {
        if (usersPath == null || usersPath.isBlank()) {
            throw new RuntimeException("user.file.path is missing in config.properties");
        }
        if (transactionPath == null || transactionPath.isBlank()) {
            throw new RuntimeException("transaction.file.path is missing in config.properties");
        }
        if (bookingsPath == null || bookingsPath.isBlank()) {
            throw new RuntimeException("booking.file.path is missing in config.properties");
        }
        if (slotPath == null || slotPath.isBlank()) {
            throw new RuntimeException("slots.file.path is missing in config.properties");
        }
    }

    private void ensureFileExists(String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create file: " + path, e);
        }
    }

    public String getUsersPath() {return usersPath;}
    public String getTransactionPath() {return transactionPath;}
    public String getBookingsPath() {return bookingsPath;}
    public String getSlotPath() {return slotPath;}
    public String getManagerPath() {return managerPath;}
}
