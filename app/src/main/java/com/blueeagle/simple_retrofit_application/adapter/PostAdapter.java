package com.blueeagle.simple_retrofit_application.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blueeagle.simple_retrofit_application.R;
import com.blueeagle.simple_retrofit_application.model.Feed;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private List<Feed> feedList;

    public PostAdapter(List<Feed> feedList) {
        this.feedList = feedList;
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);

        return new PostHolder(v);
    }

    @Override
    public void onBindViewHolder(PostHolder holder, int position) {
        holder.bindData(feedList.get(position));
        holder.postId = feedList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvBody)
        TextView tvBody;

        @BindView(R.id.tvNumOfComment)
        TextView tvNumOfComment;

        int postId;

        PostHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        void bindData(Feed feed) {
            tvTitle.setText(feed.getTitle());
            tvBody.setText(feed.getBody());
            tvNumOfComment.setText(feed.getNumOfComments() + " comments");
        }

        @Override
        public void onClick(View view) {
        }
    }
}
