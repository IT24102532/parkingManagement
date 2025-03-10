package lk.sliit.parkingmanagement.oopapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.filePath = filePath;
        this.listType = TypeToken.getParameterized(ArrayList.class, type).getType();
    }

    // CRUD Operations ---------------------------------------------------------

    // Read all entries
    public List<T> readAll() {
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }
    }

    // Create/Update entire file
    public void writeAll(List<T> entries) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(entries, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write JSON file", e);
        }
    }

    // Add new entry
    public void create(T entry) {
        List<T> entries = readAll();
        entries.add(entry);
        writeAll(entries);
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

    private String getDateField(T entry) { // Remove unused parameter
        try {
            return (String) entry.getClass().getDeclaredField("date").get(entry);
        } catch (Exception e) {
            throw new RuntimeException("Field 'date' not found", e);
        }
    }
}
