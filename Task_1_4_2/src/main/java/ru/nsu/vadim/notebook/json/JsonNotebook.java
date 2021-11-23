package ru.nsu.vadim.notebook.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.nsu.vadim.notebook.Note;
import ru.nsu.vadim.notebook.Notebook;
import ru.nsu.vadim.notebook.json.adapters.LocalDateTimeDeserializer;
import ru.nsu.vadim.notebook.json.adapters.LocalDateTimeSerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Equivalent to <code>Notebook</code> that uses JSON file as memory.
 */
public class JsonNotebook {
    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm");
    private final GsonBuilder gsonBuilder;
    private final Gson gson;
    private final Notebook notebook;
    /**
     * Default path for JSON file
     */
    public static final Path DEFAULT_PATH = Path.of("notes.json");
    private final Path filePath;

    /**
     * Creates notebook with <code>DEFAULT_PATH</code> as path to JSON file
     */
    public JsonNotebook() {
        this(DEFAULT_PATH);
    }

    /**
     * Creates notebook with defined path to JSON file
     *
     * @param filePath path to JSON file
     */
    public JsonNotebook(Path filePath) {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
        this.notebook = new Notebook();
        this.filePath = filePath;
    }

    /**
     * Returns notebook
     *
     * @return notebook
     */
    public Notebook getNotebook() {
        return notebook;
    }

    /**
     * Returns path to JSON File
     *
     * @return path
     */
    public Path getFilePath() {
        return filePath;
    }

    private void loadFile() throws IOException {
        if (Files.exists(filePath)) {
            FileReader reader = new FileReader(filePath.toFile());
            notebook.setNoteList(Arrays.asList(
                    gson.fromJson(reader, Note[].class)));
            reader.close();
        }
    }

    private void saveFile() throws IOException {
        if (Files.notExists(filePath)) {
            Files.createFile(filePath);
        }
        FileWriter writer = new FileWriter(filePath.toFile());
        gson.toJson(notebook.getNoteList().toArray(), writer);
        writer.close();
    }

    /**
     * Adds note
     *
     * @param dateTime date of note
     * @param title    title of note
     * @param text     text of note
     * @throws IOException while open and close JSON file
     */
    public void add(LocalDateTime dateTime, String title, String text) throws IOException {
        loadFile();
        notebook.add(dateTime, title, text);
        saveFile();
    }

    /**
     * Removes note with the title
     *
     * @param title title of note
     * @throws IOException while open and close JSON file
     */
    public void remove(String title) throws IOException {
        loadFile();
        notebook.remove(title);
        saveFile();
    }

    /**
     * Returns note list of notebook sorted by date
     *
     * @return sorted note list
     * @throws IOException while open and close JSON file
     */
    public List<Note> getNoteListSortedByCreationDate() throws IOException {
        loadFile();
        return notebook.getNoteListSortedByCreationDate();
    }

    /**
     * Returns note list of notebook sorted by date in date interval
     *
     * @param from date
     * @param to   date
     * @return sorted note list
     * @throws IOException while open and close JSON file
     */
    public List<Note> getNoteListByInterval(LocalDateTime from, LocalDateTime to) throws IOException {
        loadFile();
        return notebook.getNoteListByInterval(from, to);
    }

    /**
     * Returns date and time formatter that used
     *
     * @return formatter
     */
    public static DateTimeFormatter getDateTimeFormatter() {
        return DATE_TIME_FORMATTER;
    }

    /**
     * Sets date and time formatter that used
     *
     * @param dateTimeFormatter formatter
     */
    public static void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        DATE_TIME_FORMATTER = dateTimeFormatter;
    }
}
