package me.toxicmushroom.broodtimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Merlijn on 18/02/2018.
 */

public class InputDialogSure extends AppCompatDialogFragment {

    private InputDialogSureListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_sure, null);
        builder.setView(view)
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .setPositiveButton("ok", (dialog, which) -> {
                    listener.okEvent();
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (InputDialogSureListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement InputDialogSureListener");
        }
    }

    public interface InputDialogSureListener {
        void okEvent();
    }
}