package lk.sliit.parkingmanagement.oopapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class JsonHelper<T> {
    private final Gson gson;
    private final String filePath;
    private final Type listType;

    public JsonHelper(String filePath, Class<T> type) {
        this.filePath = filePath;

        RuntimeTypeAdapterFactory<User> typeFactory = RuntimeTypeAdapterFactory
                .of(User.class, "userType")
                .registerSubtype(Customer.class, "user")
                .registerSubtype(Admin.class, "admin");

        this.gson = new GsonBuilder()
                .registerTypeAdapterFactory(typeFactory)
                .setPrettyPrinting()
                .create();

        this.listType = TypeToken.getParameterized(ArrayList.class, type).getType();
    }

    // CRUD Operations ---------------------------------------------------------

    // Read all entries
    public List<T> readAll() {
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    // Create/Update entire file
    public void writeAll(List<T> entries) {
        System.out.println("JSON file path: " + new File(filePath).getAbsolutePath());
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        System.out.println("Writing to: " + file.getAbsolutePath());
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(entries, writer);
            System.out.println("Successfully wrote JSON");
        } catch (IOException e) {
            System.out.println("Failed to write JSON: " + e.getMessage());
        }
    }

    // Add new entry
    public void create(T entry) {
        List<T> entries = readAll();
        System.out.println("Before adding: " + entries.size());
        entries.add(entry);
        writeAll(entries);
        System.out.println("After adding: " + entries.size());
    }

    // Find by predicate (e.g., ID match)
    public T findOne(Predicate<T> predicate) {
        return readAll().stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    // Update entry
    public void update(Predicate<T> predicate, T newEntry) {
        List<T> entries = readAll();
        for (int i = 0; i < entries.size(); i++) {
            if (predicate.test(entries.get(i))) {
                entries.set(i, newEntry);
                break;
            }
        }
        writeAll(entries);
    }

    // Delete entry
    public void delete(Predicate<T> predicate) {
        List<T> entries = readAll();
        entries.removeIf(predicate);
        writeAll(entries);
    }

    // Sorting ----------------------------------------------------------------

    public List<T> sort(String fieldName, boolean ascending) {
        List<T> entries = readAll();
        Comparator<T> comparator = switch(fieldName) {
            case "price" -> Comparator.comparingDouble(this::getDoubleField);
            case "date" -> Comparator.comparing(this::getDateField);
            case "slotId" -> Comparator.comparing(this::getStringField);
            default -> throw new IllegalArgumentException("Invalid sort field");
        };

        if (!ascending) comparator = comparator.reversed();
        entries.sort(comparator);
        return entries;
    }

    // Helper methods for sorting
    private double getDoubleField(T entry) {
        try {
            return entry.getClass().getDeclaredField("price").getDouble(entry);
        } catch (Exception e) {
            throw new RuntimeException("Field 'price' not found", e);
        }
    }

    private String getStringField(T entry) {
        try {
            return (String) entry.getClass().getDeclaredField("slotId").get(entry);
        } catch (Exception e) {
            throw new RuntimeException("Field 'slotId' not found", e);
        }
    }

    private String getDateField(T entry) {
        try {
            return (String) entry.getClass().getDeclaredField("date").get(entry);
        } catch (Exception e) {
            throw new RuntimeException("Field 'date' not found", e);
        }
    }
}
