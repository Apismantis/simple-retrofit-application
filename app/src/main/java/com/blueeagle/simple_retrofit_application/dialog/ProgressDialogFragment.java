package com.blueeagle.simple_retrofit_application.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.blueeagle.simple_retrofit_application.R;

public class ProgressDialogFragment extends AppCompatDialogFragment {

    public ProgressDialogFragment() {
        // Default constructor
    }

    public static ProgressDialogFragment newInstance() {
        ProgressDialogFragment pdf = new ProgressDialogFragment();
        pdf.setCancelable(false);
        return pdf;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_progress, container, false);
    }
}
