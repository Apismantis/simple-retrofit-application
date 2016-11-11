package com.blueeagle.simple_retrofit_application.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.blueeagle.simple_retrofit_application.R;
import com.blueeagle.simple_retrofit_application.activity.MainActivity;
import com.blueeagle.simple_retrofit_application.adapter.CommentAdapter;
import com.blueeagle.simple_retrofit_application.model.Comment;
import com.blueeagle.simple_retrofit_application.webserviceinterface.APIClient;
import com.blueeagle.simple_retrofit_application.webserviceinterface.WebServiceInterface;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentDialogFragment extends AppCompatDialogFragment {

    @BindView(R.id.rcvComment)
    RecyclerView rcvComment;

    @BindView(R.id.loadingProgress)
    LinearLayout loadingProgress;

    private static int postId;
    private CommentAdapter adapter;
    private List<Comment> commentList;
    Call<List<Comment>> getComments;
    GetCommentDelegate getCommentDelegate;

    public CommentDialogFragment() {
    }

    public static CommentDialogFragment newInstance(int postId) {
        CommentDialogFragment.postId = postId;
        return new CommentDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_comment, null);
        builder.setView(view);

        ButterKnife.bind(this, view);

        loadingProgress.setVisibility(View.VISIBLE);
        rcvComment.setVisibility(View.GONE);

        // Init list comment
        rcvComment.setHasFixedSize(true);
        rcvComment.setLayoutManager(new LinearLayoutManager(view.getContext()));
        commentList = new ArrayList<>();
        adapter = new CommentAdapter(commentList);
        rcvComment.setAdapter(adapter);

        WebServiceInterface wsi = APIClient
                .getAPIClient("http://jsonplaceholder.typicode.com/")
                .create(WebServiceInterface.class);

        getComments = wsi.getComments(postId);
        getCommentDelegate = new GetCommentDelegate(this);
        getComments.enqueue(getCommentDelegate);

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    class GetCommentDelegate implements Callback<List<Comment>> {

        private WeakReference<CommentDialogFragment> weakReference;

        GetCommentDelegate(CommentDialogFragment commentDialogFragment) {
            weakReference = new WeakReference<>(commentDialogFragment);
        }

        @Override
        public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
            Log.d("CommentDialogFragment", "Get response...");
            CommentDialogFragment commentDialogFragment = weakReference.get();

            commentDialogFragment.loadingProgress.setVisibility(View.GONE);
            commentDialogFragment.commentList.addAll(response.body());
            commentDialogFragment.adapter.notifyDataSetChanged();
            commentDialogFragment.rcvComment.setVisibility(View.VISIBLE);

        }

        @Override
        public void onFailure(Call<List<Comment>> call, Throwable t) {
            CommentDialogFragment commentDialogFragment = weakReference.get();
            commentDialogFragment.loadingProgress.setVisibility(View.GONE);
            commentDialogFragment.rcvComment.setVisibility(View.VISIBLE);
        }
    }
}
