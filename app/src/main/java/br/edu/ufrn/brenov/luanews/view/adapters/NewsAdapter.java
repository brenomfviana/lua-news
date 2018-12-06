package br.edu.ufrn.brenov.luanews.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sun.syndication.feed.synd.SyndEntry;
import java.util.List;
import br.edu.ufrn.brenov.luanews.R;

public class NewsAdapter extends BaseAdapter {

    private List<SyndEntry> items;
    private Context context;
    private OnClickReadLaterListener readLaterListener;
    private OnClickListener listener;

    public NewsAdapter(Context context, List<SyndEntry> items, OnClickReadLaterListener readLaterListener,
            OnClickListener listener) {
        this.context = context;
        this.items = items;
        this.readLaterListener= readLaterListener;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public SyndEntry getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.news_item, viewGroup, false);
        TextView title = view.findViewById(R.id.news_text);
        title.setText(this.items.get(i).getTitle());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(i);
            }
        });
        ImageView readLater = view.findViewById(R.id.news_read_later);
        readLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readLaterListener.onClickReadLater(i);
            }
        });
        return view;
    }

    public interface OnClickReadLaterListener {
        void onClickReadLater(int i);
    }

    public interface OnClickListener {
        void onClick(int i);
    }
}
