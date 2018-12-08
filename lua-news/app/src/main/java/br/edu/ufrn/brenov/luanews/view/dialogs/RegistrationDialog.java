package br.edu.ufrn.brenov.luanews.view.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

public class RegistrationDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private OnClickListener listener;

    public static void show(FragmentManager manager, OnClickListener listener) {
        RegistrationDialog dialog = new RegistrationDialog();
        dialog.listener = listener;
        dialog.show(manager, "RegistrationDialog");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Warning")
                .setMessage("A user has already been registered, do you want to continue?")
                .setPositiveButton("Yes",this)
                .setNegativeButton("No", this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        this.listener.onClick(dialogInterface, i);
    }

    public interface OnClickListener {
        void onClick(DialogInterface dialogInterface, int i);
    }
}
