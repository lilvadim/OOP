package ru.nsu.vadim.notebook;


public class Note {
    //    из-за бага в gson тут именно String, а не LocalDateTime >:(
    private String dateTime;
    private String title;
    private String text;

    public Note(String dateTime, String title, String text) {
        this.dateTime = dateTime;
        this.title = title;
        this.text = text;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }
}
