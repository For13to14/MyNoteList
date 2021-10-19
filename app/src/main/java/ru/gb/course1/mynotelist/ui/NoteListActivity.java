package ru.gb.course1.mynotelist.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import ru.gb.course1.mynotelist.R;
import ru.gb.course1.mynotelist.domain.NoteEntity;
import ru.gb.course1.mynotelist.domain.NotesRepo;
import ru.gb.course1.mynotelist.impl.NoteRepoImpl;

/*
Главное активити со списком заметок.
Для отображения используем RecycleView
*/

public class NoteListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotesRepo notesRepo = new NoteRepoImpl();
    private NoteListAdapter adapter = new NoteListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        //test заполнение временными данными списка
        NoteEntity note1 = new NoteEntity("title1", "text1");
        notesRepo.createNote(note1);
        notesRepo.createNote(new NoteEntity("title2", "text12"));
        notesRepo.createNote(new NoteEntity("title3", "text123"));
        notesRepo.createNote(new NoteEntity("title4", "text1234"));
        notesRepo.createNote(new NoteEntity("title5", "text12345"));

        initRecycle();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_note_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_note_item:
                openAddNoteActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openAddNoteActivity() {
        Toast.makeText(this, "add note", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, NoteEditActivity.class);
        startActivity(intent);
    }


    private void initRecycle() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setList(notesRepo.getNotes());


    }




}