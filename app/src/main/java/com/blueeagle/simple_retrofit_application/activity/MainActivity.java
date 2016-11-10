package com.blueeagle.simple_retrofit_application.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.blueeagle.simple_retrofit_application.R;
import com.blueeagle.simple_retrofit_application.adapter.PostAdapter;
import com.blueeagle.simple_retrofit_application.model.Feed;
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

public class MainActivity extends BaseActivity {

    @BindView(R.id.rcvListOfPost)
    RecyclerView rcvListOfPost;

    private List<Feed> listOfFeeds;
    private PostAdapter postAdapter;
    private Call<List<Feed>> callGetFeeds;
    private GetFeedsDelegate getFeedsDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind view
        ButterKnife.bind(this);

        // Init recycle view for post
        rcvListOfPost.setHasFixedSize(true);
        rcvListOfPost.setLayoutManager(new LinearLayoutManager(this));
        listOfFeeds = new ArrayList<>();

        //initFeedData();

        postAdapter = new PostAdapter(listOfFeeds);
        rcvListOfPost.setAdapter(postAdapter);

        WebServiceInterface feedServiceInterface = APIClient
                .getAPIClient("http://jsonplaceholder.typicode.com/")
                .create(WebServiceInterface.class);

        callGetFeeds = feedServiceInterface.getAllPost();
        getFeedsDelegate = new GetFeedsDelegate(this);
        showProgressDialog();
        callGetFeeds.enqueue(getFeedsDelegate);
    }

    class GetFeedsDelegate implements Callback<List<Feed>> {

        private WeakReference<MainActivity> activityWeakReference;

        GetFeedsDelegate(MainActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void onResponse(Call<List<Feed>> call, Response<List<Feed>> response) {
            MainActivity activity = activityWeakReference.get();
            if(activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
                activity.dismissProgressDialog();
                activity.listOfFeeds.addAll(response.body());
                activity.postAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<List<Feed>> call, Throwable t) {
            MainActivity activity = activityWeakReference.get();
            if(activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
                activity.dismissProgressDialog();
                if(t != null) {
                    LOG.error(t.getMessage());
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        dismissDialog("CommentDialog");
        super.onBackPressed();
    }
}
