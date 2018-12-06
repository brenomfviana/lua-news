package br.edu.ufrn.brenov.luanews.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.model.RSSChannel;

public class RSSChannelAdapter extends BaseAdapter {

    private List<RSSChannel> items;
    private Context context;
    private OnClickFavListener favListener;
    private OnClickRemoveListener removeListener;

    public RSSChannelAdapter(Context context, List<RSSChannel> items, OnClickFavListener favListener,
                             OnClickRemoveListener removeListener) {
        this.context = context;
        this.items = items;
        this.favListener = favListener;
        this.removeListener = removeListener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public RSSChannel getItem(int i) {
        return this.items.get(i);
    }

    public RSSChannel remove(int i) {
        return this.items.remove(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        TextView title = view.findViewById(R.id.item_text);
        title.setText(this.items.get(i).getName());
        final ImageView fav = view.findViewById(R.id.item_fav);
        if (items.get(i).isFav()) {
            fav.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favListener.onClickFav(i);
            }
        });
        ImageView remove = view.findViewById(R.id.item_remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeListener.onClickRemove(i);
            }
        });
        return view;
    }

    public interface OnClickFavListener {
        void onClickFav(int i);
    }

    public interface OnClickRemoveListener {
        void onClickRemove(int i);
    }
}
