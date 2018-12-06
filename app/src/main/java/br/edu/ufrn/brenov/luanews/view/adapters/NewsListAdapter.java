package br.edu.ufrn.brenov.luanews.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.model.NewsList;
import br.edu.ufrn.brenov.luanews.view.activities.NewsListActivity;

public class NewsListAdapter extends BaseAdapter {

    private List<NewsList> items;
    private Context context;
    private OnClickEditListener editListener;
    private OnClickRemoveListener removeListener;
    private OnClickListener listener;

    public NewsListAdapter(Context context, List<NewsList> items, OnClickEditListener editListener,
                           OnClickRemoveListener removeListener, OnClickListener listener) {
        this.context = context;
        this.items = items;
        this.editListener = editListener;
        this.removeListener = removeListener;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public NewsList getItem(int i) {
        return this.items.get(i);
    }

    public NewsList remove(int i) {
        return this.items.remove(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.list_of_news_item, viewGroup, false);
        TextView title = view.findViewById(R.id.lon_item_text);
        title.setText(this.items.get(i).getName());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(i);
            }
        });
        ImageView edit = view.findViewById(R.id.lon_item_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editListener.onClickEdit(i);
            }
        });
        ImageView remove = view.findViewById(R.id.lon_item_remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeListener.onClickRemove(i);
            }
        });
        return view;
    }

    public interface OnClickEditListener {
        void onClickEdit(int i);
    }

    public interface OnClickRemoveListener {
        void onClickRemove(int i);
    }

    public interface OnClickListener {
        void onClick(int i);
    }
}
