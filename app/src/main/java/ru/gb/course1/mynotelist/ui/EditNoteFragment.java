package ru.gb.course1.mynotelist.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private final String NOTE_DATA_KEY = "note_data_key";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.edit_note_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleNoteEditText = view.findViewById(R.id.title_note_edit_text);
        textNoteEditText = view.findViewById(R.id.text_note_edit_text);
        saveNoteButton = view.findViewById(R.id.save_note_button);

        Bundle args = getArguments();
        NoteEntity note = args.getParcelable(NOTE_DATA_KEY);
        titleNoteEditText.setText(note.getTitleNote());
        textNoteEditText.setText(note.getTextNote());

        saveNoteButton.setOnClickListener(v -> {
            returnNote("save_note");
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_note_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_note_item:
                returnNote("delete_note");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void returnNote(String actionKey) {
        //save note to repo
        NoteEntity note = new NoteEntity(titleNoteEditText.getText().toString(), textNoteEditText.getText().toString());
        Bundle result = new Bundle();
        result.putString("action_key", actionKey);
        result.putParcelable("bundleKey", note);
        getParentFragmentManager().setFragmentResult("requestKey", result);


        //get list fragment back
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public EditNoteFragment newInstance(NoteEntity note) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(fragment.NOTE_DATA_KEY, note);
        fragment.setArguments(bundle);
        return fragment;
    }

}
