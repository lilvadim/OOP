package ru.nsu.vadim.notebook;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Notebook {
    private List<Note> noteList;

    /**
     * Creates new empty notebook
     */
    public Notebook() {
        noteList = new ArrayList<>();
    }

    public void remove(String title) {
        noteList = noteList.stream()
                .filter(n -> !n.getTitle().equals(title))
                .toList();
    }

    /**
     * Adds note to note list.
     *
     * @param dateTime date of note
     * @param title    title of note
     * @param text     text of note
     */
    public void add(LocalDateTime dateTime, String title, String text) {
        remove(title);
        noteList = new ArrayList<>(noteList);
        noteList.add(
                new Note(dateTime, title, text));
    }

    /**
     * Returns list of notes sorted by dates
     *
     * @return sorted list of notes
     */
    public List<Note> getNoteListSortedByCreationDate() {
        return noteList.stream().sorted(
                Comparator.comparing(Note::getDateTime)
        ).toList();
    }

    /**
     * Returns list of notes sorted by date and in date interval
     *
     * @param from date
     * @param to   date
     * @return sorted list of notes
     */
    public List<Note> getNoteListByInterval(LocalDateTime from, LocalDateTime to) {
        return getNoteListSortedByCreationDate().stream().filter(
                n -> n.getDateTime().compareTo(from) >= 0
                        && n.getDateTime().compareTo(to) <= 0
        ).toList();
    }

    /**
     * Returns list of notes
     *
     * @return list of notes
     */
    public List<Note> getNoteList() {
        return noteList;
    }

    /**
     * Sets list of notes
     *
     * @param noteList list of notes
     */
    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }
}
