package ru.gb.course1.mynotelist.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
        fillDefaultNotes();
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

    private void initRecycle() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setList(notesRepo.getNotes());
        adapter.setOnItemClickListener(this::onItemClick);


    }

    private void openAddNoteActivity() {
        Intent intent = new Intent(this, NoteEditActivity.class);
        startActivity(intent);
    }

    private void onItemClick(NoteEntity item) {
        Intent intent = new Intent(this, NoteEditActivity.class);
        intent.putExtra("item", item.getTitleNote());
        intent.putExtra("text", item.getTextNote());
        int id = notesRepo.getId(item);
        startActivityForResult(intent, id);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if (intent == null) {return;}
        notesRepo.updateNote(requestCode, new NoteEntity(intent.getStringExtra("title"), intent.getStringExtra("text")));
    }

    private void fillDefaultNotes() {
        String title = "title";
        StringBuilder text = new StringBuilder();
        text.append("text");
        for (int i = 1; i < 16; i++) {
            text.append(i);
            notesRepo.createNote(new NoteEntity(title+i, text.toString()));
        }

    }




}