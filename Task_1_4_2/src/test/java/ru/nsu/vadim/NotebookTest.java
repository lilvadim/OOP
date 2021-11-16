package ru.nsu.vadim;

import org.junit.jupiter.api.Test;
import ru.nsu.vadim.notebook.Note;
import ru.nsu.vadim.notebook.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NotebookTest {

    @Test
    void add() throws IOException {
        Path filePath = Path.of("notes.json");
        Storage st = new Storage();
        String testTitle = "Test Title";
        String testText = "test TEXT";
        String testTitle1 = "Test ttttitle";
        String testText1 = "testdd TEEEEEXT";
        st.add(LocalDateTime.now().toString(), testTitle, testText);
        st.add(LocalDateTime.now().toString(), testTitle1, testText1);
        String res = Files.readString(filePath);
        assertTrue(res.contains(testTitle));
        assertTrue(res.contains(testTitle1));
        assertTrue(res.contains(testText));
        assertTrue(res.contains(testText1));
        Files.delete(filePath);
    }

    @Test
    void remove() throws IOException {
        Path filePath = Path.of("notes.json");
        Storage st = new Storage();
        String testTitle = "Test Title";
        String testText = "test TEXT";
        String testTitle1 = "Test ttttitle";
        String testText1 = "testdd TEEEEEXT";
        st.add(LocalDateTime.now().toString(), testTitle, testText);
        st.add(LocalDateTime.now().toString(), testTitle1, testText1);
        st.remove(testTitle);
        String res = Files.readString(filePath);
        assertFalse(res.contains(testText));
        assertFalse(res.contains(testTitle));
        Files.delete(filePath);
    }

    @Test
    void getNoteListSortedByCreationDate() throws IOException {
        Path filePath = Path.of("notes.json");
        Storage st = new Storage();
        String testTitle = "Test Title";
        String testText = "test TEXT";
        String testTitle1 = "Test ttttitle";
        String testText1 = "testdd TEEEEEXT";
        String testTitle2 = "Testffs ttttitle";
        String testText2 = "testdd TfdfEEEEEXT";
        st.add(LocalDateTime.now().toString(), testTitle, testText);
        st.add(LocalDateTime.now().toString(), testTitle1, testText1);
        st.add(LocalDateTime.now().toString(), testTitle2, testText2);
        List<Note> noteListSortedByCreationDate = st.getNoteListSortedByCreationDate();
        assertEquals(testTitle, noteListSortedByCreationDate.get(0).getTitle());
        assertEquals(testText1, noteListSortedByCreationDate.get(1).getText());
        assertEquals(testTitle2, noteListSortedByCreationDate.get(2).getTitle());
        Files.delete(filePath);
    }

    @Test
    void getNoteListByInterval() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        Path filePath = Path.of("notes.json");
        Storage st = new Storage();
        String testTitle = "Test Title";
        String testText = "test TEXT";
        String testTitle1 = "Test ttttitle";
        String testText1 = "testdd TEEEEEXT";
        String testTitle2 = "Testffs ttttitle";
        String testText2 = "testdd TfdfEEEEEXT";
        st.add(LocalDateTime.parse("14.04.2013 00:00", formatter).toString(), testTitle, testText);
        st.add(LocalDateTime.parse("14.04.2014 00:00", formatter).toString(), testTitle1, testText1);
        st.add(LocalDateTime.parse("14.04.2020 00:00", formatter).toString(), testTitle2, testText2);
        List<Note> noteListByInterval = st.getNoteListByInterval(
                LocalDateTime.parse("14.04.2012 00:00", formatter),
                LocalDateTime.parse("14.04.2014 00:00", formatter));
        Files.delete(filePath);
        List<Note> ref = new ArrayList<>();
        ref.add(new Note(LocalDateTime.parse("14.04.2013 00:00", formatter).toString(), testTitle, testText));
        ref.add(new Note(LocalDateTime.parse("14.04.2014 00:00", formatter).toString(), testTitle1, testText1));
        assertEquals(ref.size(), noteListByInterval.size()); // списки одинаковые, но equals не определенно на Note
    }
}
