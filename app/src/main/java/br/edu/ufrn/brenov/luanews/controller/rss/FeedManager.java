package br.edu.ufrn.brenov.luanews.controller.rss;

import android.content.Context;

import com.sun.syndication.feed.synd.SyndFeed;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.edu.ufrn.brenov.luanews.model.RSSChannel;

public class FeedManager {

    public static List<SyndFeed> update(List<RSSChannel> channels, Context context) {
        // Initialize feed
        List<SyndFeed> feeds = new ArrayList<>();
        if (!channels.isEmpty()) {
            for (RSSChannel rssc : channels) {
                // Get news
                SyndFeed feed = null;
                try {
                    RSSFeedTask task = new RSSFeedTask(context);
                    feed = task.execute(rssc.getLink()).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                feeds.add(feed);
            }
        }
        return feeds;
    }
}
