// Copy of https://github.com/google/gson/blob/main/extras/src/main/java/com/google/gson/typeadapters/RuntimeTypeAdapterFactory.java
package lk.sliit.parkingmanagement.oopapp.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RuntimeTypeAdapterFactory<T> implements TypeAdapterFactory {
    private final Class<?> baseType;
    private final String typeFieldName;
    private final Map<String, Class<?>> labelToSubtype = new HashMap<>();
    private final Map<Class<?>, String> subtypeToLabel = new HashMap<>();
    private final boolean maintainType;
    // Private constructor used internally.
    private RuntimeTypeAdapterFactory(Class<?> baseType, String typeFieldName, boolean maintainType) {
        this.baseType = baseType;
        this.typeFieldName = typeFieldName;
        this.maintainType = maintainType;
    }
    // Static factory method to create a new instance of the factory.
    public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> baseType, String typeFieldName) {
        return new RuntimeTypeAdapterFactory<>(baseType, typeFieldName, false);
    }
    // Creates the custom TypeAdapter based on the registered subtypes
    public RuntimeTypeAdapterFactory<T> registerSubtype(Class<? extends T> subtype, String label) {
        labelToSubtype.put(label, subtype);
        subtypeToLabel.put(subtype, label);
        return this;
    }

    @Override
    public <R> TypeAdapter<R> create(Gson gson, TypeToken<R> type) {
        if (type.getRawType() != baseType) return null;
// Map label strings to TypeAdapters
        final Map<String, TypeAdapter<?>> labelToAdapter = new HashMap<>();
        final Map<Class<?>, TypeAdapter<?>> subtypeToAdapter = new HashMap<>();
// Populate adapter maps from registered subtypes
        for (Map.Entry<String, Class<?>> entry : labelToSubtype.entrySet()) {
            TypeAdapter<?> adapter = gson.getDelegateAdapter(this, TypeToken.get(entry.getValue()));
            labelToAdapter.put(entry.getKey(), adapter);
            subtypeToAdapter.put(entry.getValue(), adapter);
        }

        return new TypeAdapter<R>() {
            @Override
            public void write(JsonWriter out, R value) throws IOException {
                Class<?> srcType = value.getClass();
                String label = subtypeToLabel.get(srcType);
                @SuppressWarnings("unchecked")
                TypeAdapter<R> delegate = (TypeAdapter<R>) subtypeToAdapter.get(srcType);

                if (delegate == null) {
                    throw new JsonParseException("Cannot serialize " + srcType.getName());
                }

                JsonElement jsonElement = delegate.toJsonTree(value);
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                if (!maintainType) {
                    jsonObject.remove(typeFieldName);
                }

                jsonObject.add(typeFieldName, new JsonPrimitive(label));
                gson.toJson(jsonObject, out);
            }

            @Override
            public R read(JsonReader in) throws IOException {
                JsonElement jsonElement = JsonParser.parseReader(in);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonElement typeElement = jsonObject.get(typeFieldName);

                if (typeElement == null) {
                    throw new JsonParseException("Missing type field: " + typeFieldName);
                }

                String label = typeElement.getAsString();
                @SuppressWarnings("unchecked")
                TypeAdapter<R> delegate = (TypeAdapter<R>) labelToAdapter.get(label);

                if (delegate == null) {
                    throw new JsonParseException("Unknown type: " + label);
                }
// Deserialize into the correct subtype
                return delegate.fromJsonTree(jsonObject);
            }
        }.nullSafe();
    }
}