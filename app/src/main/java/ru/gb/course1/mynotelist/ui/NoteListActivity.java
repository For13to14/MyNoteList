package ru.gb.course1.mynotelist.ui;


import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.DialogCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import ru.gb.course1.mynotelist.R;
import ru.gb.course1.mynotelist.domain.NoteEntity;


public class NoteListActivity extends AppCompatActivity implements NotesListFragment.Controller {

    private NotesListFragment listFragment;
    private final AboutFragment aboutFragment = new AboutFragment();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    private NoteEntity noteEntity;

    private final int MAIN_FRAGMENT_CONTAINER_ID = R.id.main_fragment_container;
    private final int ADDITIONAL_FRAGMENT_CONTAINER_ID = R.id.additional_fragment_container;
    private int fragmentContainerId = MAIN_FRAGMENT_CONTAINER_ID;


    private final String LIST_FRAGMENT_KEY = "LIST";
    private final String NOTE_ENTITY_KEY = "NOTE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_menu);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        if (savedInstanceState == null) {
            listFragment = new NotesListFragment();
            replaceListFragmentToMainContainer();
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

    private void replaceListFragmentToMainContainer() {
        replaceFragment(MAIN_FRAGMENT_CONTAINER_ID, listFragment, false);
    }

    private void replaceFragment(int containerID, Fragment fragment, boolean addToBackStack) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(containerID, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    private void DrawLandscapeLayout(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            listFragment = savedInstanceState.getParcelable(LIST_FRAGMENT_KEY);
        }

        replaceListFragmentToMainContainer();
        fragmentContainerId = ADDITIONAL_FRAGMENT_CONTAINER_ID;
        noteEntity = savedInstanceState.getParcelable(NOTE_ENTITY_KEY);
        if (noteEntity != null) {
            openEditNoteFragment(noteEntity);
        }

    }

    private void DrawPortraitLayout(Bundle savedInstanceState) {

        fragmentContainerId = MAIN_FRAGMENT_CONTAINER_ID;
        if (getSupportFragmentManager().findFragmentById(ADDITIONAL_FRAGMENT_CONTAINER_ID) != null) {
            noteEntity = savedInstanceState.getParcelable(NOTE_ENTITY_KEY);
            openEditNoteFragment(noteEntity);
        } else {
            listFragment = savedInstanceState.getParcelable(LIST_FRAGMENT_KEY);
            replaceFragment(MAIN_FRAGMENT_CONTAINER_ID, listFragment, false);
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

    //side menu click
    private boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.first_item:
                if (listFragment != null) {
                    listFragment.openEditNoteFragmentWithNewNote();
                }
                break;

            case R.id.second_item:
                replaceListFragmentToMainContainer();
                break;

            case R.id.third_item:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    fragmentContainerId = ADDITIONAL_FRAGMENT_CONTAINER_ID;
                } else {
                    fragmentContainerId = MAIN_FRAGMENT_CONTAINER_ID;
                }

                replaceFragment(fragmentContainerId, aboutFragment, true);
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }

    //toolbar menu button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
                break;

            default:

                if (!getSupportFragmentManager()
                        .findFragmentById(MAIN_FRAGMENT_CONTAINER_ID)
                        .onOptionsItemSelected(item)) {

                    getSupportFragmentManager()
                            .findFragmentById(ADDITIONAL_FRAGMENT_CONTAINER_ID)
                            .onOptionsItemSelected(item);
                }
                break;
        }
        return true;
    }

    //saving fragment to parcel
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LIST_FRAGMENT_KEY, listFragment);
        outState.putParcelable(NOTE_ENTITY_KEY, noteEntity);
    }


    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount()<1) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.exit_dialog_title)
                    .setCancelable(true)
                    .setMessage(R.string.exit_dialog_message)
                    .setIcon(R.drawable.ic_baseline_delete_forever_24)
                    .setPositiveButton(R.string.exit_dialog_positive_btn_text, (dialogInterface, i) ->
                            super.onBackPressed())
                    .setNegativeButton(R.string.exit_dialog_negative_btn_text, null)
                    .create()
                    .show();

        } else {
            super.onBackPressed();
        }
    }




}