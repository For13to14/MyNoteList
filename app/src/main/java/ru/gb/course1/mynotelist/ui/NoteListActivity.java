package ru.gb.course1.mynotelist.ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

import ru.gb.course1.mynotelist.R;
import ru.gb.course1.mynotelist.domain.NoteEntity;


/*
Главное активити со списком заметок.
Для отображения используем RecycleView
*/

public class NoteListActivity extends AppCompatActivity implements NotesListFragment.Controller {
    //public final String BUNDLE_KEY = "bundle_key";
    //public final String EDIT_NOTE_REQUEST_KEY = "edit_note_request_key";
    private Fragment fragment;

    private EditNoteFragment editFragment = new EditNoteFragment();
    private NoteEntity item;

    private int fragmentId;

    private DrawerLayout drawer;


    @Override
    protected void onResume() {
        super.onResume();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentId = R.id.fragment_container_2;
        } else fragmentId = R.id.fragment_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);


        if (savedInstanceState == null) {
            fragment = new NotesListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }



    }

    @Override
    public void openNoteFragment(NoteEntity note) {

        if (this.item != note ) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(fragmentId, editFragment.newInstance(note))
                    .addToBackStack(null)
                    .commit();
        }
        this.item  = note;
    }//попробовать переделать эту косоту


    private boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.first_item:
                Toast.makeText(this, "tknul v 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.second_item:
                Toast.makeText(this, "tknul v 2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.third_item:
                Toast.makeText(this, "tknul v 3", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;

    }
}