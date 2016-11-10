package com.blueeagle.simple_retrofit_application.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.blueeagle.simple_retrofit_application.R;
import com.blueeagle.simple_retrofit_application.adapter.CommentAdapter;
import com.blueeagle.simple_retrofit_application.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentDialogFragment extends AppCompatDialogFragment {

    private RecyclerView rcvComment;
    private LinearLayout loadingProgress;
    private int postId;
    private CommentAdapter adapter;
    private List<Comment> commentList;

    public CommentDialogFragment() {
    }

    public CommentDialogFragment newInstance() {
        return new CommentDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Comments");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_comment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvComment = (RecyclerView) view.findViewById(R.id.rcvComment);
        loadingProgress = (LinearLayout) view.findViewById(R.id.loadingProgress);

        rcvComment.setHasFixedSize(true);
        rcvComment.setLayoutManager(new LinearLayoutManager(view.getContext()));
        commentList = new ArrayList<>();
        adapter = new CommentAdapter(commentList);
        rcvComment.setAdapter(adapter);


    }


}
