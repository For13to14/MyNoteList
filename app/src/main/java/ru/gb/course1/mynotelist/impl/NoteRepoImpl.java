package ru.gb.course1.mynotelist.impl;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.gb.course1.mynotelist.domain.NoteEntity;
import ru.gb.course1.mynotelist.domain.NotesRepo;

public class NoteRepoImpl implements NotesRepo {

    /*
    Воплощение интерфейса репозитория записок. Хранит все записки и обрабатывает их.
     */

    ArrayList<NoteEntity> listOfNotes = new ArrayList<>();


    @Override
    public List<NoteEntity> getNotes() {
        return listOfNotes;

        /*
    Адаптер берет этим методом данные для ресайкл вью
     */

    }


    @Override
    public void createNote() {
        NoteEntity note = new NoteEntity("Title", "Text");
        listOfNotes.add(note);

        /*
        + Ошибка где-то здесь
        Список ListOfNotes был объявлен но не инициализирован
         */
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

    @Override
    public NoteEntity getNote(int id) {
        return listOfNotes.get(id);
    }
}
