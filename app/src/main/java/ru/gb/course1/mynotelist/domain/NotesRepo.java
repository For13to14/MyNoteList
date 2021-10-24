package ru.gb.course1.mynotelist.domain;

import java.util.List;

public interface NotesRepo {

    List<NoteEntity>getNotes();

    int createNote();

    void updateNote(int index, NoteEntity newNote);

    void deleteNote(int index);

    int getId(NoteEntity item);

    NoteEntity getNote(int id);

    /*

    Create-Read-Update-Delete CRUD
    список заметок:
    Сам список
    добавление заметки
    чтение заметки(ид)
    редактирование заметки(ид)
    удаление заметки(ид)
     */
}
