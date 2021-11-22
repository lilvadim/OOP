package ru.nsu.vadim.adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.nsu.vadim.notebook.Note;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(Note.DATE_TIME_FORMATTER.format(src));
    }
}
