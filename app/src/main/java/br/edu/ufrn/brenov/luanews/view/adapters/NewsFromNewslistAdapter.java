package br.edu.ufrn.brenov.luanews.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.controller.database.news.ReadLaterDatabase;
import br.edu.ufrn.brenov.luanews.model.News;

public class NewsFromNewslistAdapter extends BaseAdapter {

    private List<News> items;
    private Context context;
    private OnClickReadLaterListener readLaterListener;
    private OnClickRemoveListener removeListener;
    private OnClickListener listener;

    public NewsFromNewslistAdapter(Context context, List<News> items,
                                   OnClickReadLaterListener readLaterListener,
                                   OnClickRemoveListener removeListener, OnClickListener listener) {
        this.context = context;
        this.items = items;
        this.readLaterListener = readLaterListener;
        this.removeListener = removeListener;
        this.listener = listener;
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public News getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.news_item_list, viewGroup, false);
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
        ImageView remove = view.findViewById(R.id.news_remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeListener.onClickRemove(i);
            }
        });
        // Get read later
        try {
            for (News n : ReadLaterDatabase.getNews(this.context)) {
                if (this.items.get(i).getLink().equals(n.getLink())) {
                    readLater.setImageResource(R.drawable.ic_access_time_selected_24dp);
                    break;
                }
            }
        } catch (JSONException e) {
            Toast.makeText(context, "Read later record error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(context, "Read later record error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return view;
    }

    public interface OnClickReadLaterListener {
        void onClickReadLater(int i);
    }

    public interface OnClickRemoveListener {
        void onClickRemove(int i);
    }

    public interface OnClickListener {
        void onClick(int i);
    }
}
