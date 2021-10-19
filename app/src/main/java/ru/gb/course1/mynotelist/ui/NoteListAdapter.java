package ru.gb.course1.mynotelist.ui;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.gb.course1.mynotelist.domain.NoteEntity;

public class NoteListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<NoteEntity> list = new ArrayList<>();

    public void setList(List<NoteEntity> list) {
        this.list=list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));

    }

    private NoteEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();

    }
}
