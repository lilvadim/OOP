package ru.nsu.vadim.notebook.json.adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.nsu.vadim.notebook.json.JsonNotebook;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * Custom serializer that uses <code>DATE_TIME_FORMATTER</code> from <code>JsonNotebook</code>
 */
public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(JsonNotebook.getDateTimeFormatter().format(src));
    }
}
