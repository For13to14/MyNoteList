package ru.gb.course1.mynotelist.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteEntity implements Parcelable {
    //private int idNote;
    private final String titleNote;
    private final String textNote;
    //date and time
    private final long timeAndDate;

    public NoteEntity(String titleNote, String textNote, long timeAndDate) {
        this.titleNote=titleNote;
        this.textNote=textNote;
        this.timeAndDate=timeAndDate;
    }

    protected NoteEntity(Parcel in) {
        titleNote = in.readString();
        textNote = in.readString();
        timeAndDate = in.readLong();
    }

    public static final Creator<NoteEntity> CREATOR = new Creator<NoteEntity>() {
        @Override
        public NoteEntity createFromParcel(Parcel in) {
            return new NoteEntity(in);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    };

    public String getTitleNote() {
        return titleNote;
    }

    public String getTextNote() {
        return textNote;
    }

    //date and time
    public long getDate() {return timeAndDate;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(titleNote);
        parcel.writeString(textNote);
        parcel.writeLong(timeAndDate);
    }
}
