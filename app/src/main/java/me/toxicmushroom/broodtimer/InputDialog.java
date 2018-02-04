package me.toxicmushroom.broodtimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

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
        builder.setView(view)
                .setTitle("Tijd voor fase " + AddBroodActivity.editing)
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .setPositiveButton("ok", (dialog, which) -> {
                    String inputnummer = inputbox.getText().toString();
                    listener.applyText(inputnummer);
                });
        return builder.create();
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
