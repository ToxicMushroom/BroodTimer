package me.toxicmushroom.broodtimer.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import me.toxicmushroom.broodtimer.R;

/**
 * Created by Merlijn on 21/02/2018.
 */

public class DialogNotify extends AppCompatDialogFragment {

    private InputDialogNotifyListener listener;
    String text = "Error report to developer";

    public DialogNotify() {
        if (getArguments() != null && getArguments().get("text") != null)
            text = String.valueOf(getArguments().get("text"));
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_notify, null);
        TextView tv = view.findViewById(R.id.dialog_text);
        tv.setText(text);
        builder.setView(view).setPositiveButton("ok", (dialog, which) -> {
                    listener.okEvent();
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (InputDialogNotifyListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement InputDialogFinishedListener");
        }
    }

    public interface InputDialogNotifyListener {
        void okEvent();
    }
}