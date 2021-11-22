package ru.nsu.vadim.notebook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.nsu.vadim.adapters.LocalDateTimeDeserializer;
import ru.nsu.vadim.adapters.LocalDateTimeSerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class JsonNotebook {
    private final GsonBuilder gsonBuilder;
    private final Gson gson;
    private Notebook notebook;
    public static final Path DEFAULT_PATH = Path.of("notes.json");
    private final Path filePath;

    public JsonNotebook() {
        this(DEFAULT_PATH);
    }

    public JsonNotebook(Path filePath) {
        this(filePath, new Notebook());
    }

    public JsonNotebook(Path filePath, Notebook notebook) {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
        this.notebook = notebook;
        this.filePath = filePath;
    }

    public void setNotebook(Notebook notebook) {
        this.notebook = notebook;
    }

    public Notebook getNotebook() {
        return notebook;
    }

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

    public void add(LocalDateTime dateTime, String title, String text) throws IOException {
        loadFile();
        notebook.add(dateTime, title, text);
        saveFile();
    }

    public void remove(String title) throws IOException {
        loadFile();
        notebook.remove(title);
        saveFile();
    }

    public List<Note> getNoteListSortedByCreationDate() throws IOException {
        loadFile();
        return notebook.getNoteListSortedByCreationDate();
    }

    public List<Note> getNoteListByInterval(LocalDateTime from, LocalDateTime to) throws IOException {
        loadFile();
        return notebook.getNoteListByInterval(from, to);
    }
}
