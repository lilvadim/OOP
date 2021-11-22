package ru.nsu.vadim.notebook;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Note {
    //    из-за бага в gson тут именно String, а не LocalDateTime >:(
    private LocalDateTime dateTime;
    private String title;
    private String text;
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm");

    public Note(LocalDateTime dateTime, String title, String text) {
        this.dateTime = dateTime;
        this.title = title;
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return dateTime.equals(note.dateTime) && title.equals(note.title) && text.equals(note.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, title, text);
    }
}
