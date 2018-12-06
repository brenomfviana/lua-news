package br.edu.ufrn.brenov.luanews.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.controller.database.rss.RSSDatabase;
import br.edu.ufrn.brenov.luanews.model.RSSChannel;
import br.edu.ufrn.brenov.luanews.view.adapters.RSSChannelAdapter;

public class FavNewspapersActivity extends BaseActivity implements RSSChannelAdapter.OnClickFavListener,
        RSSChannelAdapter.OnClickRemoveListener, RSSChannelAdapter.OnClickListener {

    private ListView list;
    private List<RSSChannel> channels;
    private RSSChannelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_newspapers);
        super.onCreateDrawer();
        // Update checked news_item
        this.navigationView.getMenu().getItem(2).setChecked(true);
        // Get views
        this.list = findViewById(R.id.list_of_fav_digital_newspapers);
        // Get channels
        try {
            this.channels = new ArrayList<>();
            for (RSSChannel rssc : RSSDatabase.getRSSChannels(this)) {
                if (rssc.isFav()) {
                    this.channels.add(rssc);
                }
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Error while reading RSS feeds.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "Error while reading RSS feeds.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        this.adapter = new RSSChannelAdapter(this, this.channels, this, this,
                this, true);
        this.list.setAdapter(this.adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Update checked news_item
        this.navigationView.getMenu().getItem(2).setChecked(true);
    }

    @Override
    public void onClickFav(int i) {
        try {
            this.channels.get(i).setFav(!this.channels.get(i).isFav());
            int result = RSSDatabase.fav(this.channels.get(i), this);
            if (result == RSSDatabase.CHANNEL_FAV_CHANGED) {
                this.channels.remove(i);
                this.adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Error while favoring RSS feeds.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "Error while favoring RSS feeds.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onClickRemove(int i) {
        // Empty
    }

    @Override
    public void onClick(int i) {
        Intent intent = new Intent(this, NewsFromFeedActivity.class);
        intent.putExtra("link", this.adapter.getItem(i).getLink());
        startActivity(intent);
    }
}
