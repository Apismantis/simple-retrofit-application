package com.blueeagle.simple_retrofit_application.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.blueeagle.simple_retrofit_application.R;

import java.lang.ref.WeakReference;

public class SplashScreenActivity extends BaseActivity {

    private LoadingDataTask loadingDataTask;
    private final String TAG_PROGRESS_DIALOG = "ProgressDialog";
    private static boolean isDestroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        // Activity is running
        isDestroyed = false;

        // Init loading task
        loadingDataTask = new LoadingDataTask(this);

        // Wait 2 seconds then show progress dialog in 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDataTask.execute();
            }
        }, 1000);
    }

    public void goToMainActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        if (loadingDataTask != null && !loadingDataTask.isCancelled()) {
            LOG.debug("Canceling loading task...");
            loadingDataTask.cancel(true);
        }

        isDestroyed = true;
        super.onDestroy();
    }

    class LoadingDataTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<SplashScreenActivity> activityWeakReference;

        LoadingDataTask(SplashScreenActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            SplashScreenActivity activity = activityWeakReference.get();
            if (activity != null && !isDestroyed && !activity.isFinishing())
                activity.showDialog(TAG_PROGRESS_DIALOG);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            synchronized (this) {
                try {
                    wait(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            SplashScreenActivity activity = activityWeakReference.get();
            activityWeakReference.clear();

            if (activity != null && !isDestroyed && !activity.isFinishing()) {
                activity.dismissDialog(TAG_PROGRESS_DIALOG);
                activity.goToMainActivity();
            }
        }
    }
}
