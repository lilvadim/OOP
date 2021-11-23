package ru.nsu.vadim.notebook;


import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Note class contains title, text and date
 */
public class Note {
    //    из-за бага в gson тут именно String, а не LocalDateTime >:(
    private LocalDateTime dateTime;
    private String title;
    private String text;

    public Note(LocalDateTime dateTime, String title, String text) {
        this.dateTime = dateTime;
        this.title = title;
        this.text = text;
    }

    /**
     * Returns date
     *
     * @return date
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns title of note
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns text of note
     *
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets date of note to <code>dateTime</code>. Could be used with <code>LocalDateTime.now()</code>
     *
     * @param dateTime dateTime
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Sets title of note to <code>title</code>
     *
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets text of note to <code>text</code>
     *
     * @param text text
     */
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
