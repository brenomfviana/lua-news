package br.edu.ufrn.brenov.luanews.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sun.syndication.feed.synd.SyndEntry;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.controller.database.rss.RSSDatabase;
import br.edu.ufrn.brenov.luanews.model.RSSChannel;
import br.edu.ufrn.brenov.luanews.view.fragments.NewsFragment;

public class HomeActivity extends BaseActivity implements NewsFragment.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.onCreateDrawer();
        // Update title
        setTitle("");
        this.navigationView.getMenu().getItem(0).setChecked(true);
        // Add a channel
        try {
            List<RSSChannel> channels = RSSDatabase.getRSSChannels(this);
            for (RSSChannel channel : channels) {
                Log.d("rss", channel.getLink());
            }
            RSSChannel rss = new RSSChannel("http://feeds.bbci.co.uk/news/world/rss.xml",
                    "BBC News - World");
            RSSDatabase.add(rss, this);
            channels = RSSDatabase.getRSSChannels(this);
            for (RSSChannel channel : channels) {
                Log.d("rss", channel.getLink());
            }
            RSSDatabase.remove(rss, this);
            channels = RSSDatabase.getRSSChannels(this);
            for (RSSChannel channel : channels) {
                Log.d("rss", channel.getLink());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(SyndEntry entry) {
//        Intent intent = new Intent(this, NewsActivity.class);
//        intent.putExtra("news_title", entry.getTitle());
//        startActivity(intent);
        // Add a channel
        try {
            RSSDatabase.createDatabase(this);
            Log.e("rss", "1");
            List<RSSChannel> channels = RSSDatabase.getRSSChannels(this);
            for (RSSChannel channel : channels) {
                Log.e("rss", channel.getLink());
            }
            Log.e("rss", "2");
            RSSChannel rss = new RSSChannel("http://feeds.bbci.co.uk/news/world/rss.xml",
                    "BBC News - World");
            RSSDatabase.add(rss, this);
            channels = RSSDatabase.getRSSChannels(this);
            for (RSSChannel channel : channels) {
                Log.e("rss", channel.getLink());
            }
            Log.e("rss", "3");
            RSSDatabase.remove(rss, this);
            channels = RSSDatabase.getRSSChannels(this);
            for (RSSChannel channel : channels) {
                Log.e("rss", channel.getLink());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
