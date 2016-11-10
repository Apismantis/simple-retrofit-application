package com.blueeagle.simple_retrofit_application.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blueeagle.simple_retrofit_application.R;
import com.blueeagle.simple_retrofit_application.model.Comment;
import com.blueeagle.simple_retrofit_application.widget.StringWidget;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    private List<Comment> commentList;

    public CommentAdapter(List<Comment> comments) {
        this.commentList = comments;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);

        return new CommentHolder(v);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        holder.bindData(commentList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;

        @BindView(R.id.tvEmail)
        TextView tvEmail;

        @BindView(R.id.tvBody)
        TextView tvBody;

        CommentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(Comment comment) {
            tvName.setText(StringWidget.toCapWords(comment.getName()));
            tvEmail.setText(comment.getEmail());
            tvBody.setText(comment.getBody());
        }
    }
}
