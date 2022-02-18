package ru.gb.course1.mynotelist.domain;


import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;

public class NoteListDiffUtil extends DiffUtil.Callback{
    private final ArrayList<NoteEntity> oldList;
    private final ArrayList<NoteEntity> newList;

    public NoteListDiffUtil (ArrayList<NoteEntity> oldList, ArrayList<NoteEntity> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        //NoteEntity oldNote = oldList.get(oldItemPosition);
        //NoteEntity newNote = newList.get(newItemPosition);
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        NoteEntity oldItem = oldList.get(oldItemPosition);
        NoteEntity newItem = newList.get(newItemPosition);
        return oldItem.getTitleNote().equals(newItem.getTitleNote()) && oldItem.getTextNote().equals(newItem.getTextNote());
    }
}
