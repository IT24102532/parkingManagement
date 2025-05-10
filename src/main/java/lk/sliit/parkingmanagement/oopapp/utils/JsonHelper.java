package lk.sliit.parkingmanagement.oopapp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lk.sliit.parkingmanagement.oopapp.model.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class JsonHelper<T> {
    private final Gson gson;
    private final String filePath;
    private final Type listType;

    public JsonHelper(String filePath, Class<T> type) {
        this.filePath = filePath;

        RuntimeTypeAdapterFactory<User> userFactory = RuntimeTypeAdapterFactory
                .of(User.class, "user_type")
                .registerSubtype(Customer.class, "user")
                .registerSubtype(Admin.class, "admin");

        RuntimeTypeAdapterFactory<ParkingSlot> parkingLotFactory = RuntimeTypeAdapterFactory
                .of(ParkingSlot.class, "type")
                .registerSubtype(InstaSlot.class, "insta")
                .registerSubtype(LongTermSlot.class, "long_term");

        this.gson = new GsonBuilder()
                .registerTypeAdapterFactory(userFactory)
                .registerTypeAdapterFactory(parkingLotFactory)
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();

        this.listType = TypeToken.getParameterized(ArrayList.class, type).getType();
    }

    // -------------------------------------------
    // CRUD
    // -------------------------------------------

    //Read ALl Entries
    public List<T> readAll() {
        try (Reader reader = new FileReader(filePath)) {
            List<T> data = gson.fromJson(reader, listType);
            return data != null ? data : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    // Write ALl Entries
    public void writeAll(List<T> entries) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(entries, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create an entry
    public void create(T entry) {
        List<T> entries = readAll();
        entries.add(entry);
        writeAll(entries);
    }

    // Delete entry
    public void delete(Predicate<T> predicate) {
        List<T> entries = readAll();
        entries.removeIf(predicate);
        writeAll(entries);
    }

    // -------------------------------------------
    // Queries
    // -------------------------------------------

    // Find all matching cases
    public List<T> findAll(Predicate<T> predicate) {
        return readAll().stream().filter(predicate).collect(Collectors.toList());
    }

    /*
    *  List<ParkingSlot> slotsInColombo = slotHelper.findAll(slot ->
    *        "Colombo".equals(slot.getLocation())
    *    );
    * */

    // Filter by multiple fields

    public List<T> findByFields(Map<String, Object> criteria) {
        return readAll().stream()
                .filter(entry -> criteria.entrySet().stream()
                        .allMatch(c -> Objects.equals(getFieldValue(entry, c.getKey()), c.getValue()))
                )
                .collect(Collectors.toList());
    }

    /*
    Map<String, Object> filters = Map.of(
            "location", "Colombo",
            "type", "long-term"
    );
    List<ParkingSlot> matches = slotHelper.findByFields(filters);
    */

    // Filter by a single field
    public T findOne(Predicate<T> predicate) {
        return readAll().stream().filter(predicate).findFirst().orElse(null);
    }

    // -------------------------------------------
    // Batch Update
    // -------------------------------------------

    public void updateAll(Predicate<T> predicate, T newData) {
        List<T> entries = readAll();
        boolean updated = false;
        for (int i = 0; i < entries.size(); i++) {
            if (predicate.test(entries.get(i))) {
                entries.set(i, newData);
                updated = true;
            }
        }
        if (updated) writeAll(entries);
    }

    public void partialUpdate(Predicate<T> predicate, Map<String, Object> updates) {
        List<T> entries = readAll();
        for (T entry : entries) {
            if (predicate.test(entry)) {
                applyUpdates(entry, updates);
            }
        }
        writeAll(entries);
    }

    // -------------------------------------------
    // Sorting by any field
    // -------------------------------------------

    public List<T> sortBy(String field, boolean ascending) {
        Comparator<T> comparator = Comparator.comparing(o -> (Comparable) getFieldValue(o, field));
        if (!ascending) comparator = comparator.reversed();
        return readAll().stream().sorted(comparator).collect(Collectors.toList());
    }

    // -------------------------------------------
    // Internal Reflection Helpers
    // -------------------------------------------

    private Object getFieldValue(T entry, String fieldName) {
        try {
            Field field = findField(entry.getClass(), fieldName);
            field.setAccessible(true);
            return field.get(entry);
        } catch (Exception e) {
            throw new RuntimeException("Error accessing field '" + fieldName + "': " + e.getMessage());
        }
    }

    private Field findField(Class<?> clazz, String name) throws NoSuchFieldException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(name);
    }

    private void applyUpdates(T entry, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            try {
                Field field = findField(entry.getClass(), key);
                field.setAccessible(true);
                field.set(entry, value);
            } catch (Exception e) {
                throw new RuntimeException("Failed to update field '" + key + "'", e);
            }
        });
    }
}

