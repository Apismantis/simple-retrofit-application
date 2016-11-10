package com.blueeagle.simple_retrofit_application.activity;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.blueeagle.simple_retrofit_application.dialog.CommentDialogFragment;
import com.blueeagle.simple_retrofit_application.dialog.ProgressDialogFragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseActivity extends AppCompatActivity {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    protected static final String TAG_PROGRESS_DIALOG = "ProgressDialog";
    protected static final String TAG_COMMENT_DIALOG = "CommentDialog";

    public void showDialog(String dialogTag, int postId) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prevFrag = getSupportFragmentManager().findFragmentByTag(dialogTag);

        if (prevFrag == null) {
            DialogFragment newFrag = getDialogFragment(dialogTag, postId);
            ft.add(newFrag, dialogTag);
        } else {
            ft.remove(prevFrag);
        }

        ft.commitAllowingStateLoss();
    }

    private DialogFragment getDialogFragment(String dialogTag, int postId) {
        DialogFragment dialogFragment = null;
        switch (dialogTag) {
            case TAG_PROGRESS_DIALOG:
                dialogFragment = ProgressDialogFragment.newInstance();
                break;

            case TAG_COMMENT_DIALOG:
                dialogFragment = CommentDialogFragment.newInstance(postId);
                break;

        }

        return dialogFragment;
    }

    public void dismissDialog(String dialogTag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prevFrag = getSupportFragmentManager().findFragmentByTag(dialogTag);

        if (prevFrag != null) {
            ft.remove(prevFrag);
        }

        ft.commitAllowingStateLoss();
    }

    public void showProgressDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prevFrag = getSupportFragmentManager().findFragmentByTag(TAG_PROGRESS_DIALOG);

        if (prevFrag == null) {
            ProgressDialogFragment newFrag = ProgressDialogFragment.newInstance();
            ft.add(newFrag, TAG_PROGRESS_DIALOG);
        } else {
            ft.remove(prevFrag);
        }

        ft.commitAllowingStateLoss();
    }

    public void dismissProgressDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prevFrag = getSupportFragmentManager().findFragmentByTag(TAG_PROGRESS_DIALOG);

        if (prevFrag != null) {
            ft.remove(prevFrag);
        }

        ft.commitAllowingStateLoss();
    }
}
