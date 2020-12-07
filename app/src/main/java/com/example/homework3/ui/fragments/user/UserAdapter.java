package com.example.homework3.ui.fragments.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework3.R;
import com.example.homework3.data.models.Post;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<Post> postList;
    private OnClicks onClicks;

    public UserAdapter(List<Post> postList, OnClicks onClicks) {
        this.postList = postList;
        this.onClicks = onClicks;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(postList.get(position));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public Post getItem(int pos){
        return postList.get(pos);
    }

    public void removeItem(int pos) {
        postList.remove(pos);
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView userNum;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClicks.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClicks.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
            userNum = itemView.findViewById(R.id.user_name);
        }

        public void bind(Post post) {
            userNum.setText("user: "+post.getUser());
        }
    }

    public interface OnClicks{
        void onItemClick(int pos);
        void onItemLongClick(int pos);
    }
}
