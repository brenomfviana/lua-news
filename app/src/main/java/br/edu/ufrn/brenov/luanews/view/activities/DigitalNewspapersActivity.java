package br.edu.ufrn.brenov.luanews.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.util.List;
import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.controller.database.rss.RSSDatabase;
import br.edu.ufrn.brenov.luanews.model.RSSChannel;
import br.edu.ufrn.brenov.luanews.view.adapters.RSSChannelAdapter;

public class DigitalNewspapersActivity extends BaseActivity implements RSSChannelAdapter.OnClickFavListener, RSSChannelAdapter.OnClickRemoveListener {

    private ListView list;
    private List<RSSChannel> channels;
    private RSSChannelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_newspapers);
        super.onCreateDrawer();
        // Update checked news_item
        this.navigationView.getMenu().getItem(1).setChecked(true);
        // Get views
        this.list = findViewById(R.id.list_of_digital_newspapers);
        // Get channels
        try {
            this.channels = RSSDatabase.getRSSChannels(this);
        } catch (JSONException e) {
            Toast.makeText(this, "Error while reading RSS feeds.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "Error while reading RSS feeds.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        this.adapter = new RSSChannelAdapter(this, this.channels, this, this);
        this.list.setAdapter(this.adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Get news
        try {
            this.channels = RSSDatabase.getRSSChannels(this);
        } catch (JSONException e) {
            Toast.makeText(this, "Error while reading RSS feeds.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "Error while reading RSS feeds.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        this.adapter = new RSSChannelAdapter(this, this.channels, this, this);
        this.list.setAdapter(this.adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.digital_newspapers_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_newspaper:
                startActivity(new Intent(this, AddNewspaperActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickFav(int i) {
        try {
            this.channels.get(i).setFav(!this.channels.get(i).isFav());
            int result = RSSDatabase.fav(this.channels.get(i), this);
            if (result == RSSDatabase.CHANNEL_FAV_CHANGED) {
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
        try {
            int result = RSSDatabase.remove(this.channels.get(i), this);
            if (result == RSSDatabase.CHANNEL_REMOVED) {
                this.channels.remove(i);
                this.adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Error while removing RSS feeds.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "Error while removing RSS feeds.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
