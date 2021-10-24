package ru.gb.course1.mynotelist.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.gb.course1.mynotelist.R;
import ru.gb.course1.mynotelist.domain.NoteEntity;


/*
Главное активити со списком заметок.
Для отображения используем RecycleView
*/

public class NoteListActivity extends AppCompatActivity implements NotesListFragment.Controller {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new NotesListFragment())
                .commit();

    }

    @Override
    public void openNoteFragment(NoteEntity item) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new EditNoteFragment().newInstance(item))
                .addToBackStack(null)
                .commit();
    }

}