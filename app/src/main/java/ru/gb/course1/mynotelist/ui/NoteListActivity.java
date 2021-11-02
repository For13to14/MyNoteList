package ru.gb.course1.mynotelist.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import ru.gb.course1.mynotelist.R;
import ru.gb.course1.mynotelist.domain.NoteEntity;


/*
Главное активити со списком заметок.
Для отображения используем RecycleView
*/

public class NoteListActivity extends AppCompatActivity implements NotesListFragment.Controller {
    public final String BUNDLE_KEY = "bundle_key";
    public final String EDIT_NOTE_REQUEST_KEY = "edit_note_request_key";
    private Fragment fragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        if (savedInstanceState == null) {
            fragment = new NotesListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }

    }

    @Override
    public void openNoteFragment(NoteEntity item) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new EditNoteFragment().newInstance(item))
                .addToBackStack(null)
                .commit();
    }

}