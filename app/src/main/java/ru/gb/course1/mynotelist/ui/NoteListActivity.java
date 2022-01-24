package ru.gb.course1.mynotelist.ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

import ru.gb.course1.mynotelist.R;
import ru.gb.course1.mynotelist.domain.NoteEntity;


public class NoteListActivity extends AppCompatActivity implements NotesListFragment.Controller {

    private NotesListFragment listFragment;
    private final AboutFragment aboutFragment = new AboutFragment();

    private NoteEntity noteEntity;

    private int fragmentContainerId = R.id.main_fragment_container;

    private final String LIST_FRAGMENT_KEY = "LIST";
    private final String NOTE_ENTITY_KEY = "NOTE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        if (savedInstanceState == null) {

            listFragment = new NotesListFragment();
            createNewMainFragment();


        } else {
            listFragment = savedInstanceState.getParcelable(LIST_FRAGMENT_KEY);
            noteEntity = savedInstanceState.getParcelable(NOTE_ENTITY_KEY);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                DrawLandscapeLayout(savedInstanceState);
            } else {
                DrawPortraitLayout(savedInstanceState);
            }
        }

    }

    private void createNewMainFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, listFragment)
                .commit();
    }

    private void DrawLandscapeLayout(Bundle savedInstanceState) {

        if (savedInstanceState !=null) {
            listFragment = savedInstanceState.getParcelable(LIST_FRAGMENT_KEY);
        }
        createNewMainFragment();
        fragmentContainerId = R.id.additional_fragment_container;
        noteEntity = savedInstanceState.getParcelable(NOTE_ENTITY_KEY);
        if (noteEntity != null) {
            openEditNoteFragment(noteEntity);
        }

    }

    private void DrawPortraitLayout(Bundle savedInstanceState) {

        fragmentContainerId = R.id.main_fragment_container;
        if (getSupportFragmentManager().findFragmentById(R.id.additional_fragment_container) != null) {

            noteEntity = savedInstanceState.getParcelable(NOTE_ENTITY_KEY);
            openEditNoteFragment(noteEntity);

        } else {
            listFragment = savedInstanceState.getParcelable(LIST_FRAGMENT_KEY);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, listFragment)
                    .commit();
        }
    }

    @Override
    public void openEditNoteFragment(NoteEntity note) {

        getSupportFragmentManager()
                    .beginTransaction()
                    .replace(fragmentContainerId, EditNoteFragment.newInstance(note))
                    .addToBackStack(null)
                    .commit();
          noteEntity = note;
    }

    private boolean onNavigationItemSelected(MenuItem item) {
        //side menu click
        switch (item.getItemId()) {
            case R.id.first_item:
                if (listFragment != null) {
                    listFragment.openEditNoteFragmentWithNewNote();
                }
                break;

            case R.id.second_item:
                Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show();
                break;

            case R.id.third_item:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    fragmentContainerId = R.id.additional_fragment_container;
                } else {
                    fragmentContainerId = R.id.main_fragment_container;
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(fragmentContainerId, aboutFragment)
                        .addToBackStack(null)
                        .commit();

                break;
        }
        return false;

    }

    //saving fragment to parcell
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LIST_FRAGMENT_KEY, listFragment);
        outState.putParcelable(NOTE_ENTITY_KEY, noteEntity);
    }


}