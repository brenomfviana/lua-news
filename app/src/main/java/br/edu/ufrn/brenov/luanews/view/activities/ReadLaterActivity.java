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
import br.edu.ufrn.brenov.luanews.controller.database.news.ReadLaterDatabase;
import br.edu.ufrn.brenov.luanews.model.News;
import br.edu.ufrn.brenov.luanews.view.adapters.NewsAdapter;

public class ReadLaterActivity extends BaseActivity implements NewsAdapter.OnClickReadLaterListener,
    NewsAdapter.OnClickListener{

    private ListView list;
    private List<News> items;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_later);
        super.onCreateDrawer();
        // Update checked news_item
        this.navigationView.getMenu().getItem(4).setChecked(true);
        // Get views
        this.list = findViewById(R.id.news_from_read_later);
        this.items = new ArrayList<>();
        try {
            // Get feeds
            this.items = ReadLaterDatabase.getNews(this);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error while reading RSS feeds.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error while reading RSS feeds.", Toast.LENGTH_SHORT).show();
        }

        this.adapter = new NewsAdapter(this, this.items, this,
                this);
        this.list.setAdapter(this.adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Update checked news_item
        this.navigationView.getMenu().getItem(4).setChecked(true);
    }

    @Override
    public void onClickReadLater(int i) {
        try {
            if (ReadLaterDatabase.contains(this.items.get(i), this)) {
                ReadLaterDatabase.remove(this.items.get(i), this);
                this.items.remove(i);
            } else {
                ReadLaterDatabase.add(this.items.get(i), this);
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Read later record error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "Read later record error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int i) {
        News news = this.items.get(i);
        Intent intent = new Intent(this, NewsActivity.class);
        intent.putExtra("news_link", news.getLink());
        intent.putExtra("news_title", news.getTitle());
        intent.putExtra("news_description", news.getDescription());
        if (news.getAuthor().equals("")) {
            intent.putExtra("news_author", "N/A");
        } else {
            intent.putExtra("news_author", news.getAuthor());
        }
        intent.putExtra("news_date", news.getPublishedDate());
        startActivity(intent);
    }
}
