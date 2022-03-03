package ru.gb.course1.mynotelist.impl;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;

import ru.gb.course1.mynotelist.domain.NoteEntity;
import ru.gb.course1.mynotelist.domain.NotesRepo;

public class NoteRepoImpl implements NotesRepo, Parcelable {

    /*
    implementation of interface Notes repository. Keeps all notes
     */

    ArrayList<NoteEntity> listOfNotes;

    @Override
    public ArrayList<NoteEntity> getNotes() {
        return listOfNotes;

    /*
    adapter takes data for Recycle View by this method
     */

    }


    @Override
    public int createNote() {
        long date = Calendar.getInstance().getTimeInMillis();
        NoteEntity note = new NoteEntity("New note", "Text", date);
        listOfNotes.add(note);
        return listOfNotes.size();

    }

    @Override
    public NoteEntity getNote(int id) {
        return listOfNotes.get(id);
    }

    @Override
    public void updateNote(int index, NoteEntity newNote) {
        listOfNotes.set(index, newNote);
    }

    @Override
    public void deleteNote(int index) {
        listOfNotes.remove(index);

    }

    @Override
    public int getId(NoteEntity item) {
        return listOfNotes.indexOf(item);
    }

    //Parcel class methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(listOfNotes);
    }

    public static final Creator<NoteRepoImpl> CREATOR = new Creator<NoteRepoImpl>() {
        @Override
        public NoteRepoImpl createFromParcel(Parcel in) {
            return new NoteRepoImpl(in);
        }

        @Override
        public NoteRepoImpl[] newArray(int size) {
            return new NoteRepoImpl[size];
        }
    };

    protected NoteRepoImpl(Parcel in) {
        listOfNotes = in.createTypedArrayList(NoteEntity.CREATOR);
    }

    public NoteRepoImpl() {
        listOfNotes = new ArrayList<>();
    }

}
