package ru.gb.course1.mynotelist.ui;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.gb.course1.mynotelist.R;
import ru.gb.course1.mynotelist.domain.NoteEntity;

public final class EditNoteFragment extends Fragment {

    private static EditNoteFragment instance;
    private static Bundle bundle;
    private EditText titleNoteEditText;
    private EditText textNoteEditText;
    //date
    private TextView dateTextView;
    private long dateAndTime;

    private final static String NOTE_DATA_KEY = "note_data_key";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.edit_note_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        instance.titleNoteEditText = view.findViewById(R.id.title_note_edit_text);
        instance.textNoteEditText = view.findViewById(R.id.text_note_edit_text);
        Button saveNoteButton = view.findViewById(R.id.save_note_button);

        //date
        dateTextView = view.findViewById(R.id.date_text_view);


        Bundle args = getArguments();
        if (args != null) {
            NoteEntity note = args.getParcelable(NOTE_DATA_KEY);
            instance.titleNoteEditText.setText(note.getTitleNote());
            instance.textNoteEditText.setText(note.getTextNote());
            //date
            dateAndTime = note.getDate();
            dateTextView.setText(DateFormatting(dateAndTime));
        }
        saveNoteButton.setOnClickListener(v -> returnNote("save_note"));

        //datePicker
        dateTextView.setOnClickListener( v-> {

            Calendar rightNow = Calendar.getInstance();
            int selectedDate = rightNow.get(Calendar.DATE);
            int selectedMonth = rightNow.get(Calendar.MONTH);
            int selectedYear = rightNow.get(Calendar.YEAR);

            DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, mouthOfYear, dayOfMonth) -> {
                //dateTextView.setText(sdf.format(rightNow.getTimeInMillis()));
                rightNow.set(Calendar.YEAR, year);
                rightNow.set(Calendar.MONTH, mouthOfYear);
                rightNow.set(Calendar.DATE, dayOfMonth);
                dateTextView.setText(DateFormatting(rightNow.getTimeInMillis()));
            };

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    dateSetListener, selectedYear, selectedMonth, selectedDate);

            datePickerDialog.show();
        });


    }

    private String DateFormatting (long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd.MM.yyyy");
        return sdf.format(date);
    }

    public static EditNoteFragment newInstance(NoteEntity note) {
        if (instance == null) {
            instance = new EditNoteFragment();
            bundle = new Bundle();
        } else {
            bundle.clear();
        }

        bundle.putParcelable(NOTE_DATA_KEY, note);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_note_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_note_item:
                deletingNote();
                return true;
            default:
                //return super.onOptionsItemSelected(item);
                return false;
        }
    }

    private void returnNote(String actionKey) {
        //save note to repo
        NoteEntity note = new NoteEntity(instance.titleNoteEditText.getText().toString(),
                instance.textNoteEditText.getText().toString(), dateAndTime);
        Bundle result = new Bundle();
        result.putString("action_key", actionKey);
        result.putParcelable("bundleKey", note);
        getParentFragmentManager().setFragmentResult("requestKey", result);

        //get list fragment back
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void deletingNote() {

        Snackbar snackbar = Snackbar.make(getView(),
                R.string.snackbar_text,
                Snackbar.LENGTH_LONG);
        snackbar.show();

        snackbar.setAction(R.string.snackbar_button_text,
                view -> returnNote("delete_note"));

    }
}
