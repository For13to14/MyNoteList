package ru.gb.course1.mynotelist.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.course1.mynotelist.R;
import ru.gb.course1.mynotelist.domain.NoteEntity;

public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView titleNote = itemView.findViewById(R.id.note_title_text_view);
    private TextView textNote = itemView.findViewById(R.id.note_text_text_view);


    public ViewHolder(@NonNull ViewGroup parent, int viewType) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
    }

    public void bind(NoteEntity item) {
        this.titleNote.setText(item.getTitleNote());
        this.textNote.setText(item.getTextNote());
    }


}
