package ru.nsu.vadim.notebook;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Notebook {
    private List<Note> noteList;

    public Notebook() {
        noteList = new ArrayList<>();
    }

    public void remove(String title) {
        noteList = noteList.stream()
                .filter(n -> !n.getTitle().equals(title))
                .toList();
    }

    public void add(LocalDateTime dateTime, String title, String text) {
        remove(title);
        noteList = new ArrayList<>(noteList);
        noteList.add(
                new Note(dateTime, title, text));
    }

    public List<Note> getNoteListSortedByCreationDate() {
        return noteList.stream().sorted(
                Comparator.comparing(Note::getDateTime)
        ).toList();
    }

    public List<Note> getNoteListByInterval(LocalDateTime from, LocalDateTime to) {
        return getNoteListSortedByCreationDate().stream().filter(
                n -> n.getDateTime().compareTo(from) >= 0
                        && n.getDateTime().compareTo(to) <= 0
        ).toList();
    }


    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }
}
