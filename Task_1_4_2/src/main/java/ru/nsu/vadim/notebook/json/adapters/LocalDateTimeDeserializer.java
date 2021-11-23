package ru.nsu.vadim.notebook.json.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import ru.nsu.vadim.notebook.json.JsonNotebook;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * Custom deserializer that uses <code>DATE_TIME_FORMATTER</code> from <code>JsonNotebook</code>
 */
public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(), JsonNotebook.getDateTimeFormatter());
    }
}
