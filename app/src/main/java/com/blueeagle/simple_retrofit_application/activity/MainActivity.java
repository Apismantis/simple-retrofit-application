package com.blueeagle.simple_retrofit_application.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blueeagle.simple_retrofit_application.R;
import com.blueeagle.simple_retrofit_application.adapter.PostAdapter;
import com.blueeagle.simple_retrofit_application.model.Comment;
import com.blueeagle.simple_retrofit_application.model.Feed;
import com.blueeagle.simple_retrofit_application.webserviceinterface.APIClient;
import com.blueeagle.simple_retrofit_application.webserviceinterface.FeedServiceInterface;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rcvListOfPost)
    RecyclerView rcvListOfPost;

    @BindView(R.id.lnError)
    LinearLayout lnError;

    @BindView(R.id.tvErrorMessage)
    TextView tvErrorMessage;

    private List<Feed> listOfFeeds;
    private PostAdapter postAdapter;

    private final String TAG_PROGRESS_DIALOG = "ProgressDialog";
    private final String TAG_COMMENT_DIALOG = "CommentDialog";
    private static boolean isDestroyed = false;

    private FeedServiceInterface feedServiceInterface;
    private LoadNumOfComment loadNumOfComment;

    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Activity is running
        isDestroyed = false;

        // Bind view
        ButterKnife.bind(this);

        // Init recycle view for list of post
        rcvListOfPost.setHasFixedSize(true);
        rcvListOfPost.setLayoutManager(new LinearLayoutManager(this));
        listOfFeeds = new ArrayList<>();
        postAdapter = new PostAdapter(listOfFeeds);
        rcvListOfPost.setAdapter(postAdapter);

        // Check network and load feed
        if (!checkNetWorkConnection()) {
            rcvListOfPost.setVisibility(View.GONE);
            lnError.setVisibility(View.VISIBLE);
            tvErrorMessage.setText(getResources().getString(R.string.message_error_can_not_connected));
        }
        else {
            rcvListOfPost.setVisibility(View.VISIBLE);
            lnError.setVisibility(View.GONE);
            loadFeed();
        }
    }

    private void loadFeed() {
        // Create new web service interface
        feedServiceInterface = APIClient
                .getAPIClient("http://jsonplaceholder.typicode.com/")
                .create(FeedServiceInterface.class);

        // Get all post and add call back to catch response
        Call<List<Feed>> callGetFeeds = feedServiceInterface.getAllPost();
        GetFeedsDelegate getFeedsDelegate = new GetFeedsDelegate(this);
        showDialog(TAG_PROGRESS_DIALOG);
        callGetFeeds.enqueue(getFeedsDelegate);
    }

    // Check network connection
    public boolean checkNetWorkConnection() {
        if (connectivityManager == null)
            connectivityManager = (ConnectivityManager) getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    class GetFeedsDelegate implements Callback<List<Feed>> {

        private WeakReference<MainActivity> activityWeakReference;

        GetFeedsDelegate(MainActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void onResponse(Call<List<Feed>> call, Response<List<Feed>> response) {
            MainActivity activity = activityWeakReference.get();
            if (activity != null && !isDestroyed && !activity.isFinishing()) {
                activity.dismissDialog(TAG_PROGRESS_DIALOG);
                activity.listOfFeeds.addAll(response.body());
                activity.postAdapter.notifyDataSetChanged();

                // Get num of comment_128
                loadNumOfComment = new LoadNumOfComment(activity);
                loadNumOfComment.execute();
            }
        }

        @Override
        public void onFailure(Call<List<Feed>> call, Throwable t) {
            MainActivity activity = activityWeakReference.get();
            if (activity != null && !isDestroyed && !activity.isFinishing()) {
                activity.dismissDialog(TAG_PROGRESS_DIALOG);

                // Display error message
                activity.rcvListOfPost.setVisibility(View.INVISIBLE);
                activity.lnError.setVisibility(View.VISIBLE);
                activity.tvErrorMessage.setText(getResources().getString(R.string.message_error_can_not_sync));

                if (t != null) {
                    LOG.error(t.getMessage());
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    class GetNumOfCommentDelegate implements Callback<List<Comment>> {

        private WeakReference<MainActivity> activityWeakReference;
        // Index of element in listOfFeeds
        private int idx;

        GetNumOfCommentDelegate(MainActivity activity, int idx) {
            activityWeakReference = new WeakReference<>(activity);
            this.idx = idx;
        }

        @Override
        public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
            MainActivity activity = activityWeakReference.get();

            if (activity != null && !isDestroyed && !activity.isFinishing()) {
                activity.listOfFeeds.get(idx).setNumOfComments(response.body().size());
                activity.postAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<List<Comment>> call, Throwable t) {
            MainActivity activity = activityWeakReference.get();

            if (activity != null && !isDestroyed && !activity.isFinishing()) {
                LOG.error(t.getMessage());
                activity.listOfFeeds.get(idx).setNumOfComments(0);
                activity.postAdapter.notifyDataSetChanged();
            }
        }
    }

    class LoadNumOfComment extends AsyncTask<Void, Void, Void> {

        private WeakReference<MainActivity> weakReference;

        LoadNumOfComment(MainActivity mainActivity) {
            weakReference = new WeakReference<>(mainActivity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MainActivity activity = weakReference.get();

            if (activity != null && !isDestroyed && !activity.isFinishing()) {
                Call<List<Comment>> callGetNumOfComment;
                GetNumOfCommentDelegate getNumOfCommentDelegate;

                for (int i = 0; i < activity.listOfFeeds.size(); i++) {
                    Feed feed = activity.listOfFeeds.get(i);
                    callGetNumOfComment = activity.feedServiceInterface.getComments(feed.getId());
                    getNumOfCommentDelegate = new GetNumOfCommentDelegate(activity, i);
                    callGetNumOfComment.enqueue(getNumOfCommentDelegate);
                }
            }

            return null;
        }
    }

    @Override
    public void onBackPressed() {
        dismissDialog(TAG_COMMENT_DIALOG);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        isDestroyed = true;

        if (loadNumOfComment != null && !loadNumOfComment.isCancelled())
            loadNumOfComment.cancel(true);

        super.onDestroy();
    }
}
