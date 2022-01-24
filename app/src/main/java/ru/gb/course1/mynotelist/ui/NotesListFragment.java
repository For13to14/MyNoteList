package ru.gb.course1.mynotelist.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import ru.gb.course1.mynotelist.R;
import ru.gb.course1.mynotelist.domain.NoteEntity;
import ru.gb.course1.mynotelist.impl.NoteRepoImpl;

public class NotesListFragment extends Fragment implements Parcelable {
    private NoteRepoImpl notesRepoImpl;
    private final NoteListAdapter adapter = new NoteListAdapter();
    private Controller controller;
    private int noteId;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        controller = (Controller) context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        setRetainInstance(true);

        // added with  delete in fragment manifest
        if (notesRepoImpl == null) {
            notesRepoImpl = new NoteRepoImpl();
        }
        if (savedInstanceState != null) {
            notesRepoImpl = savedInstanceState.getParcelable("NOTES_REPO_KEY");
            noteId = savedInstanceState.getInt("NOTE_ID_KEY");
        }

        requireActivity().getSupportFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, result) -> {
            if (result.getString("action_key").equals("save_note")) {
                notesRepoImpl.updateNote(noteId, result.getParcelable("bundleKey"));
            } else {
                notesRepoImpl.deleteNote(noteId);
            }
            adapter.notifyDataSetChanged();
        });

        return inflater.inflate(R.layout.note_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycle(view);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_note_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_note_item:
                openEditNoteFragmentWithNewNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openEditNoteFragmentWithNewNote() {
        noteId = notesRepoImpl.createNote() - 1;
        controller.openEditNoteFragment(notesRepoImpl.getNote(noteId));
    }

    private void initRecycle(View view) {
        //initialisation RecycleView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setList(notesRepoImpl.getNotes());
        adapter.setOnItemClickListener(this::onItemClick);
        //popup
        adapter.setOnLongItemClickListener((note, v) -> onLongItemClick(note,v));
    }

    private void onItemClick(NoteEntity note) {
        this.noteId = notesRepoImpl.getId(note);
        controller.openEditNoteFragment(note);
    }

    //popup
    private boolean onLongItemClick(NoteEntity note, View v)  {
        Toast.makeText(requireContext(), note.getTitleNote(), Toast.LENGTH_SHORT).show();
        showPopupMenu(note, v);
        return true;
    }

    private void showPopupMenu(NoteEntity note, View v) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), v);
        popupMenu.inflate(R.menu.view_popup_menu);


        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.popup_edit_note_item:
                    onItemClick(note);
                    break;
                case R.id.popup_delete_note_item:
                    notesRepoImpl.deleteNote(notesRepoImpl.getId(note));
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
            }
            return false;

        });

        popupMenu.show();


    }

    //kill Controller
    @Override
    public void onDestroy() {
        controller = null;
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("NOTES_REPO_KEY", notesRepoImpl);
        outState.putInt("NOTE_ID_KEY", noteId);
    }

    // Parcellable functions
    protected NotesListFragment(Parcel in) {
        noteId = in.readInt();
    }

    public NotesListFragment() {

    }

    public static final Creator<NotesListFragment> CREATOR = new Creator<NotesListFragment>() {
        @Override
        public NotesListFragment createFromParcel(Parcel in) {
            return new NotesListFragment(in);
        }

        @Override
        public NotesListFragment[] newArray(int size) {
            return new NotesListFragment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(noteId);
    }

    //interface NotesRepo
    interface Controller {
        void openEditNoteFragment(NoteEntity note);
    }


}
