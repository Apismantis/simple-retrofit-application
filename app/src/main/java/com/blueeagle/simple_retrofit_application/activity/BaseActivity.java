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

    /**
     * @param dialogTag: Tag of dialog that will be displayed
     * @param args:      Option argument. If dialogTag is TAG_COMMENT_DIALOG
     *                   ,args will be pass to this function with postId
     */
    public void showDialog(String dialogTag, int... args) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prevFrag = getSupportFragmentManager().findFragmentByTag(dialogTag);

        if (prevFrag == null) {
            DialogFragment newFrag = getNewDialogInstance(dialogTag, args);
            ft.add(newFrag, dialogTag);
        } else {
            ft.remove(prevFrag);
        }

        ft.commitAllowingStateLoss();
    }

    /**
     * Get new dialog instance
     * @param dialogTag: Tag of dialog that will be displayed
     * @param args:      Option argument. If dialogTag is TAG_COMMENT_DIALOG
     *                   ,args will be pass to this function with postId
     */
    public DialogFragment getNewDialogInstance(String dialogTag, int... args) {
        DialogFragment dialogFragment = null;

        switch (dialogTag) {
            case TAG_PROGRESS_DIALOG:
                dialogFragment = ProgressDialogFragment.newInstance();
                break;

            case TAG_COMMENT_DIALOG:
                if (args.length != 0)
                    dialogFragment = CommentDialogFragment.newInstance(args[0]);
                break;

        }

        return dialogFragment;
    }

    // Dismiss a dialog
    public void dismissDialog(String dialogTag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prevFrag = getSupportFragmentManager().findFragmentByTag(dialogTag);

        if (prevFrag != null) {
            ft.remove(prevFrag);
        }

        ft.commitAllowingStateLoss();
    }
}
