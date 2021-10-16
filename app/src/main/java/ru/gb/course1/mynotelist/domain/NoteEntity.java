package ru.gb.course1.mynotelist.domain;

public class NoteEntity {
    //private int idNote;
    private String titleNote;
    private String textNote;

    //todo date and time

    public NoteEntity(String titleNote, String textNote) {
        this.titleNote=titleNote;
        this.textNote=textNote;
    }

    public String getTitleNote() {
        return titleNote;
    }

    public String getTextNote() {
        return textNote;
    }
}
