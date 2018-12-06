package br.edu.ufrn.brenov.luanews.view.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.controller.database.rss.RSSDatabase;
import br.edu.ufrn.brenov.luanews.model.RSSChannel;

public class AddNewspaperActivity extends AppCompatActivity {

    private EditText edtRSSFeedName;
    private EditText edtRSSFeedLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_newspaper);
        // Update action bar color
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#06706c")));
        // Get views
        this.edtRSSFeedName = findViewById(R.id.edt_rss_feed_name);
        this.edtRSSFeedLink = findViewById(R.id.edt_rss_feed_link);
    }

    public void addRSSFeed(View view) {
        // Get values
        String name = this.edtRSSFeedName.getText().toString();
        String link = this.edtRSSFeedLink.getText().toString();
        // Show error for invalid feed
        if (name.equals("")) {
            edtRSSFeedName.setError("Invalid RSS Feed name!");
        }
        if (link.equals("")) {
            edtRSSFeedLink.setError("Invalid RSS Feed link!");
        }
        // Check if is a valid feed
        if (!name.equals("") && !link.equals("")) {
            // Create RSS Feed
            try {
                RSSChannel rss = new RSSChannel(link, name, false);
                int result = RSSDatabase.add(rss, this);
                // Show message feedback
                switch (result) {
                    case RSSDatabase.CHANNEL_ADDED:
                        Toast.makeText(this, "RSS Feed registered.", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        finish();
                        break;
                    case RSSDatabase.CHANNEL_ALREADY_ADDED:
                        edtRSSFeedLink.setError("This RSS Feed already registered!");
                        break;
                    case RSSDatabase.INVALID_CHANNEL:
                        Toast.makeText(this, "Error: Invalid RSS Feed.", Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (JSONException e) {
                Toast.makeText(this, "RSS Feed record error.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(this, "RSS Feed record error.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
