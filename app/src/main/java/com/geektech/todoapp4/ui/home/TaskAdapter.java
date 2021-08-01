package com.geektech.todoapp4.ui.home;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.todoapp4.OnItemClickListener;
import com.geektech.todoapp4.R;
import com.geektech.todoapp4.ui.models.Task;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    ArrayList<Task> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void addItem(Task task) {
        list.add(0, task);
        notifyItemInserted(list.indexOf(task));
    }

    public void addItems(List<Task> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Task getItem(int position) {
        return list.get(position);
    }

    public void removeItem(int position) {
        list.get(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView, textDate;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textTitle);
            textDate = itemView.findViewById(R.id.textDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void onBind(Task task) {
            textView.setText(task.getTite());
            textDate.setText(DateUtils.formatDateTime
                    (itemView.getContext(), task.getCreatedAt(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                            | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }
    }
}
