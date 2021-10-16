package ru.gb.course1.mynotelist.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.course1.mynotelist.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView titleNote;
    public TextView textNote;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        titleNote = itemView.findViewById(R.id.note_title_text_view);
        textNote = itemView.findViewById(R.id.note_text_text_view);


        /*
        Класс для представления конкретного айтема в ресайкл вью
         */
    }
}
