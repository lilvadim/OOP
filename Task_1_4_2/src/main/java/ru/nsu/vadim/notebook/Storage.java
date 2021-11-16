package ru.nsu.vadim.notebook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class Storage {
    private GsonBuilder gsonBuilder;
    private Gson gson;
    private List<Note> noteList;
    public static final Path DEFAULT_PATH = Path.of("notes.json");
    public final Path filePath;

    public Storage() throws IOException {
        this(DEFAULT_PATH);
    }

    public Storage(Path filePath) throws IOException {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
        noteList = new ArrayList<>();
        this.filePath = filePath;
    }

    private void loadFile() throws IOException {
        if (Files.exists(filePath)) {
            FileReader reader = new FileReader(filePath.toFile());
            noteList = Arrays.asList(
                    gson.fromJson(reader, Note[].class));
            reader.close();
        }
    }

    private void saveFile() throws IOException {
        if (Files.notExists(filePath)) {
            Files.createFile(filePath);
        }
        FileWriter writer = new FileWriter(filePath.toFile());
        gson.toJson(noteList.toArray(), writer);
        writer.close();
    }

    public void add(String dateTime, String title, String text) throws IOException {
        loadFile();
        noteList = new ArrayList<>(noteList);
        noteList.add(
                new Note(dateTime, title, text));
        saveFile();
    }

    public void remove(String title) throws IOException {
        loadFile();
        noteList = noteList.stream()
                .filter(n -> !n.getTitle().equals(title))
                .toList();
        saveFile();
    }

    public List<Note> getNoteListSortedByCreationDate() {
        return noteList.stream().sorted(
                (a, b) -> {
                    LocalDateTime dateA = LocalDateTime.parse(a.getDateTime());
                    LocalDateTime dateB = LocalDateTime.parse(b.getDateTime());
                    return dateA.compareTo(dateB);
                }
        ).toList();
    }

    public List<Note> getNoteListByInterval(LocalDateTime from, LocalDateTime to) {
        return getNoteListSortedByCreationDate().stream().filter(
                n -> {
                    LocalDateTime localDateTime = LocalDateTime.parse(n.getDateTime());
                    if (localDateTime.compareTo(from) >= 0
                            && localDateTime.compareTo(to) <= 0) {
                        return true;
                    }
                    return false;
                }
        ).toList();
    }
}
