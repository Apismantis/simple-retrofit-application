package com.blueeagle.simple_retrofit_application.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.blueeagle.simple_retrofit_application.R;

import java.lang.ref.WeakReference;

public class SplashScreenActivity extends BaseActivity {

    private LoadingDataTask loadingDataTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        // Init loading task
        loadingDataTask = new LoadingDataTask(this);

        // Wait 2 seconds then show progress dialog in 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDataTask.execute();
            }
        }, 2 * 1000);
    }

    public void goToMainActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        if (loadingDataTask != null && !loadingDataTask.isCancelled()) {
            loadingDataTask.cancel(true);
        }

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
            if (activity != null && !activity.isDestroyed() && !activity.isFinishing())
                activity.showProgressDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            synchronized (this) {
                try {
                    wait(3 * 1000);
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

            if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
                activity.dismissProgressDialog();
                activity.goToMainActivity();
            }
        }
    }
}
