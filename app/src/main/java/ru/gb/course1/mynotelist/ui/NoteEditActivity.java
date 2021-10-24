package ru.gb.course1.mynotelist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.gb.course1.mynotelist.R;

public class NoteEditActivity extends AppCompatActivity {

    /*
    добавить обработчик меню
     */
    private EditText titleNoteEditText;
    private EditText textNoteEditText;
    private Button saveNoteButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        titleNoteEditText = findViewById(R.id.title_note_edit_text);
        textNoteEditText = findViewById(R.id.text_note_edit_text);
        saveNoteButton = findViewById(R.id.save_note_button);

        saveNoteButton.setOnClickListener(this::onClick);

        Intent intent = getIntent();

        titleNoteEditText.setText(intent.getStringExtra("title"));
        textNoteEditText.setText(intent.getStringExtra("text"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_note_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("title", titleNoteEditText.getText().toString());
        intent.putExtra("text", textNoteEditText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
