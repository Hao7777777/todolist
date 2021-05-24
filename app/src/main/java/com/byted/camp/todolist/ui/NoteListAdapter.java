package com.byted.camp.todolist.ui;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.byted.camp.todolist.NoteOperator;
import com.byted.camp.todolist.R;
import com.byted.camp.todolist.beans.Note;
import com.byted.camp.todolist.operation.activity.SettingActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2019/1/23.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public class NoteListAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private final NoteOperator operator;
    private final List<Note> notes = new ArrayList<>();

    private SharedPreferences mSharedPreferences;

    public NoteListAdapter(NoteOperator operator) {
        this.operator = operator;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void refresh(List<Note> newNotes) {
        notes.clear();
        if (newNotes != null) {
            notes.addAll(newNotes);

            //todo 根据${com.byted.camp.todolist.operation.activity.SettingActivity} 中设置的sp控制是否将已完成的完成排到最后，默认不排序

            final boolean sortByState = mSharedPreferences.getBoolean(SettingActivity.getSPName(), false);
            if (sortByState) {
                newNotes = newNotes.stream()
                        .sorted(Comparator.comparing(Note::getState)
                                .thenComparing(Comparator.comparing(Note::getPriority).reversed()))
                        .collect(Collectors.toList());
            } else {
                newNotes = newNotes.stream()
                        .sorted(Comparator.comparing(Note::getPriority).reversed())
                        .collect(Collectors.toList());
            }
            notes.addAll(newNotes);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int pos) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView, operator);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int pos) {
        holder.bind(notes.get(pos));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
