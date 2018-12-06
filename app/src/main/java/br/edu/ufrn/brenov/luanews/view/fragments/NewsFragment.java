package br.edu.ufrn.brenov.luanews.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.controller.database.rss.RSSDatabase;
import br.edu.ufrn.brenov.luanews.controller.rss.FeedManager;
import br.edu.ufrn.brenov.luanews.model.RSSChannel;
import br.edu.ufrn.brenov.luanews.view.adapters.SyndEntryAdapter;

public class NewsFragment extends Fragment {

    private ViewGroup container;
    private List<RSSChannel> channels;
    private List<SyndFeed> feeds;
    private OnItemClickListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Show exception
        if (!(context instanceof OnItemClickListener)) {
            throw new RuntimeException("The context must be OnItemClick.");
        }
        this.listener = (OnItemClickListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.container = container;
        try {
            // Get feeds
            this.channels = RSSDatabase.getRSSChannels(getContext());
            this.feeds = FeedManager.update(channels, getContext());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error while reading RSS feeds.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error while reading RSS feeds.", Toast.LENGTH_SHORT).show();
        }
        // Show respective fragment
        if (this.feeds.isEmpty()) {
            return inflater.inflate(R.layout.no_news_fragment, container, false);
        } else {
            return inflater.inflate(R.layout.news_fragment, container, false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.feeds.isEmpty()) {
            // Get news
            this.feeds = FeedManager.update(this.channels, getContext());
        } else {
            // Show all news
            List<SyndEntry> items = new ArrayList<>();
            for (Object entry : this.feeds.get(0).getEntries()) {
                SyndEntry e = (SyndEntry) entry;
                items.add(e);
            }
            SyndEntryAdapter adapter = new SyndEntryAdapter(getContext(), items);
            ListView listView = (ListView) getView();
            listView.setAdapter(adapter);
            // Set news_item click method
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                    if (listener != null) {
                        listener.onClick((SyndEntry) adapter.getAdapter().getItem(i));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(SyndEntry entry);
    }
}
