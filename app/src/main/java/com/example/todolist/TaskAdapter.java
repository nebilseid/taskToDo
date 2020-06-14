package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.todolist.model.DataToDo;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {


    Context C;
    private List<DataToDo> toDos;
    private onItemClickListener taskClickListener;
     String url = "https://picsum.photos/200/300";

    public interface onItemClickListener {
        public void itemClick(int position);
    }

    void setOnItemClickListener(onItemClickListener listener) {
        taskClickListener = listener;
    }

    void setData(List<DataToDo> toDoTasks) {
        this.toDos = toDoTasks;
        toDos.addAll(toDoTasks);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task, parent, false);
        return new MyViewHolder(v, taskClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvTitle.setText(toDos.get(position).getTitle());
        holder.tvDetails.setText(toDos.get(position).getDescription());
      /*  Glide.with(holder.itemView.getContext())
                .load(toDos.get(position).getUrl())
                .into(holder.ivIcon);*/
    }

    @Override
    public int getItemCount() {
        return toDos.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDetails;
        ImageView ivIcon;

        MyViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_task_title);
            tvDetails = (TextView) itemView.findViewById(R.id.tv_task_detail);
            ivIcon = itemView.findViewById(R.id.iv_task_icon);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.itemClick(position);
                        }
                    }

                }

            });
        }
    }
}

