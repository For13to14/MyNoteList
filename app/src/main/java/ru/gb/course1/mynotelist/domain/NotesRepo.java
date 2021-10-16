package ru.gb.course1.mynotelist.domain;

import java.util.List;

public interface NotesRepo {

    List<NoteEntity>getNotes();

    void createNote(NoteEntity newNote);

    void updateNote(int index, NoteEntity newNote);

    void deleteNote(int index);

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
