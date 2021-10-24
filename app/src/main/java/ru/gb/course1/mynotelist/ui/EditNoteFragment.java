package ru.gb.course1.mynotelist.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.gb.course1.mynotelist.R;
import ru.gb.course1.mynotelist.domain.NoteEntity;

public class EditNoteFragment extends Fragment {

    private EditText titleNoteEditText;
    private EditText textNoteEditText;
    private Button saveNoteButton;

    private final String NOTE_KEY = "note_key";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_note_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleNoteEditText = view.findViewById(R.id.title_note_edit_text);
        textNoteEditText = view.findViewById(R.id.text_note_edit_text);
        saveNoteButton = view.findViewById(R.id.save_note_button);

        Bundle args = getArguments();
        NoteEntity note = args.getParcelable(NOTE_KEY);
        titleNoteEditText.setText(note.getTitleNote());
        textNoteEditText.setText(note.getTextNote());
        saveNoteButton.setOnClickListener(v -> {
            saveNoteButtonClick();
        });

    }

    private void saveNoteButtonClick() {

    }

    public static EditNoteFragment newInstance(NoteEntity note) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(fragment.NOTE_KEY, note);
        fragment.setArguments(bundle);
        return fragment;
    }
}
