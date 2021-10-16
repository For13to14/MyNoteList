package ru.gb.course1.mynotelist.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.gb.course1.mynotelist.R;

public class NoteEditActivity extends AppCompatActivity {

    /*
    Пока пустое активити для редактирования заметки
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_note_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
