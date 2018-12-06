package br.edu.ufrn.brenov.luanews.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import org.json.JSONException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.controller.database.rss.RSSDatabase;
import br.edu.ufrn.brenov.luanews.controller.rss.FeedManager;
import br.edu.ufrn.brenov.luanews.model.RSSChannel;
import br.edu.ufrn.brenov.luanews.view.activities.HomeActivity;
import br.edu.ufrn.brenov.luanews.view.activities.NewsActivity;
import br.edu.ufrn.brenov.luanews.view.adapters.NewsAdapter;

import static br.edu.ufrn.brenov.luanews.view.activities.HomeActivity.*;

public class NewsFragment extends Fragment implements OnTyppingListener,
    OnClickReadLaterListener, OnClickListener {

    private ViewGroup container;
    private List<RSSChannel> channels;
    private List<SyndFeed> feeds;
    private List<SyndEntry> items;
    private List<SyndEntry> allItems;
    private NewsAdapter adapter;

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
            this.items = new ArrayList<>();
            this.allItems = new ArrayList<>();
            for (Object entry : this.feeds.get(0).getEntries()) {
                SyndEntry e = (SyndEntry) entry;
                this.items.add(e);
                this.allItems.add(e);
            }
            this.adapter = new NewsAdapter(getContext(), this.items,
                    (HomeActivity) getContext(), (HomeActivity) getContext());
            ListView listView = (ListView) getView();
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onTypping(String text) {
        this.items.clear();
        for (SyndEntry entry : this.allItems) {
            if (entry.getTitle().toLowerCase().contains(text.toLowerCase())) {
                this.items.add(entry);
            }
        }
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickReadLater(int i) {
    }

    @Override
    public void onClick(int i) {
        SyndEntry entry = this.items.get(i);
        Intent intent = new Intent(getContext(), NewsActivity.class);
        intent.putExtra("news_link", entry.getLink());
        intent.putExtra("news_title", entry.getTitle());
        intent.putExtra("news_description", entry.getDescription().getValue());
        if (entry.getAuthor().equals("")) {
            intent.putExtra("news_author", "N/A");
        } else {
            intent.putExtra("news_author", entry.getAuthor());
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(entry.getPublishedDate());
        intent.putExtra("news_date", date);
        startActivity(intent);
    }
}
