package ru.gb.course1.mynotelist.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import ru.gb.course1.mynotelist.R;
import ru.gb.course1.mynotelist.domain.NoteEntity;
import ru.gb.course1.mynotelist.domain.NotesRepo;
import ru.gb.course1.mynotelist.impl.NoteRepoImpl;

public class NotesListFragment extends Fragment {
    private RecyclerView recyclerView;
    private NotesRepo notesRepo = new NoteRepoImpl();
    private NoteListAdapter adapter = new NoteListAdapter();
    private Controller controller;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        controller = (Controller) context;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.note_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        initRecycle(view);
        fillDefaultNotes();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_note_list_activity, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_note_item:
                //openAddNoteActivity();
                //todo open new note
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fillDefaultNotes() {
        for (int i = 1; i < 16; i++) {
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

    private void onItemClick(NoteEntity item) {
        controller.openNoteFragment(item);
    }


    interface Controller {
        void openNoteFragment(NoteEntity item);
    }


}
