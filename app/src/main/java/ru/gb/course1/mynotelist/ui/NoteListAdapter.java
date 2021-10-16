package ru.gb.course1.mynotelist.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.gb.course1.mynotelist.R;
import ru.gb.course1.mynotelist.domain.NoteEntity;

public class NoteListAdapter extends RecyclerView.Adapter<ViewHolder> {

    /*
    Адаптер для ресайкл вью
     */

    private List<NoteEntity> list = new ArrayList<>();

    public void setList(List<NoteEntity> list) {
        this.list=list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);

        /*
        Родной метод адаптера. Возвращает объект ViewHolder который будет
        хранить данные по одному объекту. Списан с видеоурока 6.
         */
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteEntity note = getItem(position);
        holder.titleNote.setText(note.getTitleNote());
        holder.textNote.setText(note.getTextNote());

        /*
        Родной метод адаптера. Выполняет привязку объекта ViewHolder к объекту note по
        определённой позиции.
         */
    }

    private NoteEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();

        /*
        Родной метод адаптера. Возвращает количество элементов в списке
        теперь ошибка здесь - нулевой размер адаптера
         */
    }
}
