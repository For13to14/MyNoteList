package ru.gb.course1.mynotelist.ui;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.gb.course1.mynotelist.domain.NoteEntity;


public class NoteListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<NoteEntity> list = new ArrayList<>();
    private OnItemClickListener clickListener = null;
    //popup
    private OnLongItemClickListener longClickListener = null;


    public void setList(List<NoteEntity> list) {
        this.list=list;
        notifyDataSetChanged();
    }

    public ArrayList<NoteEntity> getList() {
        return (ArrayList<NoteEntity>) list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //popup
        return new ViewHolder(parent, clickListener, longClickListener);
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }

    //popup
    public void setOnLongItemClickListener(OnLongItemClickListener listener) {
        longClickListener = listener;
    }

    interface OnItemClickListener {
        void onItemClick(NoteEntity item);
    }
    //popup
    interface OnLongItemClickListener {
        boolean onLongItemClick(NoteEntity item, View view);
    }
}
