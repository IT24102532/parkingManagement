package lk.sliit.parkingmanagement.oopapp.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

    @Override
    public JsonElement serialize(LocalTime localTime, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(localTime.format(formatter));
    }

    @Override
    public LocalTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        return LocalTime.parse(jsonElement.getAsString(), formatter);
    }
}
