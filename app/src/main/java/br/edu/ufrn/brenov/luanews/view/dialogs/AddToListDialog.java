package br.edu.ufrn.brenov.luanews.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.util.List;
import br.edu.ufrn.brenov.luanews.controller.database.news.NewsListDatabase;
import br.edu.ufrn.brenov.luanews.model.News;
import br.edu.ufrn.brenov.luanews.model.NewsList;

public class AddToListDialog extends DialogFragment implements DialogInterface.OnMultiChoiceClickListener {

    private News news;
    private List<NewsList> nls;
    private boolean[] checked;
    private CharSequence[] items;

    public static void show(FragmentManager manager, Context context, News n) {
        AddToListDialog dialog = new AddToListDialog();
        try {
            dialog.nls = NewsListDatabase.getNewsLists(context);
            dialog.checked = new boolean[dialog.nls.size()];
            dialog.items = new CharSequence[dialog.nls.size()];
            dialog.news = n;
            int i = 0;
            for (NewsList nl : dialog.nls) {
                dialog.items[i] = nl.getName();
                if (nl == null && nl.isEmpty()) {
                    dialog.checked[i] = false;
                    continue;
                }
                dialog.checked[i] = false;
                for (News n1 : nl.getNewslist()) {
                    if (n1.getLink().equals(n.getLink())) {
                        dialog.checked[i] = true;
                        break;
                    }
                }
                i++;
            }
        } catch (JSONException e) {
            Toast.makeText(context, "List record error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(context, "List record error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        dialog.show(manager, "AddToListDialog");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Select the lists to add the news:")
                .setMultiChoiceItems(this.items, this.checked, this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
        NewsList nl = this.nls.get(i);
        if (b) {
            nl.add(news);
        } else {
            nl.remove(news);
        }
        try {
            NewsListDatabase.update(nl, getContext());
        } catch (JSONException e) {
            Toast.makeText(getContext(), "List record error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(getContext(), "List record error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
