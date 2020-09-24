package com.example.gogaga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> postList;

    public PostAdapter(List<Post> p) {
        this.postList = p;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post p = postList.get(position);
        holder.titleTextView.setText(p.getTitle());
        holder.bodyTextView.setText(p.getBody());
        holder.idTextView.setText(String.valueOf(p.getId()));
    }

    @Override
    public int getItemCount() {
        if (postList != null) {
            return postList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView bodyTextView;
        TextView idTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_txtview);
            bodyTextView = itemView.findViewById(R.id.body_txtview);
            idTextView = itemView.findViewById(R.id.user_id_text_view);
        }
    }

    public void addData(List<Post> pList) {
        postList.addAll(pList);
        notifyDataSetChanged();
    }
}
