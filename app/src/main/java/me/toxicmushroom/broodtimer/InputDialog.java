package me.toxicmushroom.broodtimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import me.toxicmushroom.broodtimer.activities.AddBroodActivity;

/**
 * Created by Merlijn on 27/12/2017.
 */

public class InputDialog extends AppCompatDialogFragment {

    private InputDialogListener listener;
    EditText inputbox = null;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        inputbox = view.findViewById(R.id.dialog_input);
        inputbox.setText(AddBroodActivity.valueOfEditing);
        inputbox.setSelection(inputbox.getText().length());

        builder.setView(view)
                .setTitle("Tijd voor: " + MainActivity.faseToName(AddBroodActivity.editing))
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .setPositiveButton("ok", (dialog, which) -> {
                    String inputnummer = inputbox.getText().toString();
                    listener.applyText(inputnummer);
                });

        final AlertDialog alertDialog = builder.create();
        inputbox.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                listener.applyText(inputbox.getText().toString());
                alertDialog.dismiss();
                return true;
            } else {
                return false;
            }
        });

        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return alertDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (InputDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement InputDialogListener");
        }
    }

    public interface InputDialogListener {
        void applyText(String input);
    }
}
