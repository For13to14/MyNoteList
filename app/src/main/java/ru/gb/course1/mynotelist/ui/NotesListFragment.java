package ru.gb.course1.mynotelist.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import ru.gb.course1.mynotelist.R;
import ru.gb.course1.mynotelist.domain.NoteEntity;
import ru.gb.course1.mynotelist.domain.NotesRepo;
import ru.gb.course1.mynotelist.impl.NoteRepoImpl;

public class NotesListFragment extends Fragment {
    private RecyclerView recyclerView;
    private final NotesRepo notesRepo = new NoteRepoImpl();
    private final NoteListAdapter adapter = new NoteListAdapter();
    private Controller controller;
    private int noteId;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        controller = (Controller) context;

        //fillDefaultNotes();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        requireActivity().getSupportFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (result.getString("action_key").equals("save_note")) {
                    notesRepo.updateNote(noteId, result.getParcelable("bundleKey"));
                } else {
                    notesRepo.deleteNote(noteId);
                }
                adapter.notifyDataSetChanged();
            }
        });


        return inflater.inflate(R.layout.note_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycle(view);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_note_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_note_item:
                this.noteId = notesRepo.createNote() - 1;
                controller.openNoteFragment(notesRepo.getNote(noteId));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fillDefaultNotes() {
        for (int i = 1; i < 5; i++) {
            notesRepo.createNote();
        }

    }

    private void initRecycle(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setList(notesRepo.getNotes());
        adapter.setOnItemClickListener(this::onItemClick);


    }

    private void onItemClick(NoteEntity note) {
        this.noteId = notesRepo.getId(note);
        controller.openNoteFragment(note);
    }

    @Override
    public void onDestroy() {
        controller = null;
        super.onDestroy();
    }

    interface Controller {
        void openNoteFragment(NoteEntity note);
    }


}
