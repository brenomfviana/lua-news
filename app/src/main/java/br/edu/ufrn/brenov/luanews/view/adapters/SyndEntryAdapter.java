package br.edu.ufrn.brenov.luanews.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sun.syndication.feed.synd.SyndEntry;
import java.util.List;

import br.edu.ufrn.brenov.luanews.R;

public class SyndEntryAdapter extends BaseAdapter {

    private List<SyndEntry> items;
    private Context context;

    public SyndEntryAdapter(Context context, List<SyndEntry> items) {
        this.context = context;
        this.items = items;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.news_item, viewGroup, false);
        TextView title = view.findViewById(R.id.news_text);
        title.setText(this.items.get(i).getTitle());
        return view;
    }
}
