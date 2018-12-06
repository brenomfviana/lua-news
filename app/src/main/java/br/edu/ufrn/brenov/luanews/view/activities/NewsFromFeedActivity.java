package br.edu.ufrn.brenov.luanews.view.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import br.edu.ufrn.brenov.luanews.view.adapters.NewsAdapter;

public class NewsFromFeedActivity extends AppCompatActivity implements NewsAdapter.OnClickReadLaterListener,
    NewsAdapter.OnClickListener {

    private ListView list;
    private List<SyndEntry> items;
    private List<RSSChannel> channel;
    private List<SyndFeed> feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_from_feed);
        // Update action bar color
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#06706c")));
        // Get views
        this.list = findViewById(R.id.news_from_feed);
        // Get feed link
        String link = getIntent().getExtras().getString("link");
        this.channel = new ArrayList<>();
        try {
            // Get feeds
            List<RSSChannel> channels = RSSDatabase.getRSSChannels(this);
            for (RSSChannel rss : channels) {
                if (rss.getLink().equals(link)) {
                    this.channel.add(rss);
                    this.feed = FeedManager.update(channel, this);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error while reading RSS feeds.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error while reading RSS feeds.", Toast.LENGTH_SHORT).show();
        }
        this.items = new ArrayList<>();
        for (Object entry : this.feed.get(0).getEntries()) {
            SyndEntry e = (SyndEntry) entry;
            items.add(e);
        }
        NewsAdapter adapter = new NewsAdapter(this, items, this,
                this);
        this.list.setAdapter(adapter);
    }

    @Override
    public void onClickReadLater(int i) {
    }

    @Override
    public void onClick(int i) {
        SyndEntry entry = this.items.get(i);
        Intent intent = new Intent(this, NewsActivity.class);
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
