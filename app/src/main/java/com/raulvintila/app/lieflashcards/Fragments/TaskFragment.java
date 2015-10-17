package com.raulvintila.app.lieflashcards.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;

import de.greenrobot.event.util.ErrorDialogManager;

public class TaskFragment extends DialogFragment implements MaterialDialog.ListCallback {

    private MaterialDialog dan;
    private String dialogTag;
    //MyTask mTask;

    // This is also called by the AsyncTask.
    /*public void taskFinished()
    {
        // Make sure we check if it is resumed because we will crash if trying to dismiss the dialog
        // after the user has switched to another app.
        if (isResumed())
            dismiss();

        // If we aren't resumed, setting the task to null will allow us to dimiss ourselves in
        // onResume().
        //mTask = null;

        // Tell the fragment that we are done.
*//*        if (getTargetFragment() != null)
            getTargetFragment().onActivityResult(TASK_FRAGMENT, Activity.RESULT_OK, null);*//*
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        dan = new MaterialDialog.Builder(getActivity())
                .title("Synchronizing your data")
                .content("Please wait...")
                .progress(true, 0)
                .show();
        return dan;
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();
    }

    @Override
    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence s) {
    }

    public void dismiss(FragmentManager manager)
    {
        TaskFragment dialog = (TaskFragment)manager.findFragmentByTag(dialogTag);
        if (dialog != null)
        {
            dialog.dismiss();
        }
    }

    @Override
    public void show(FragmentManager manager, String tag)
    {
        dialogTag = tag;
        super.show(manager, dialogTag);
    }
}
