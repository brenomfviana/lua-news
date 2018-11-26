package br.edu.ufrn.brenov.luanews.controller.rss;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RSSFeedTask extends AsyncTask<String, Void, SyndFeed> {

    private Context context;

    public RSSFeedTask(Context context) {
        this.context = context;
    }

    @Override
    protected SyndFeed doInBackground(String... urls) {
        InputStream is = null;
        try {
            URL url = new URL(urls[0]);
            URLConnection conn = url.openConnection();
            conn.connect();
            int lenghtOfFile = conn.getContentLength();
            is = url.openStream();
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(is));
            return feed;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FeedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(SyndFeed feed) {
        Toast.makeText(this.context, feed.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
