package br.edu.ufrn.brenov.luanews.view.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.controller.database.news.NewsListDatabase;
import br.edu.ufrn.brenov.luanews.controller.database.news.ReadLaterDatabase;
import br.edu.ufrn.brenov.luanews.model.News;
import br.edu.ufrn.brenov.luanews.model.NewsList;
import br.edu.ufrn.brenov.luanews.view.adapters.NewsFromNewslistAdapter;

public class NewsListActivity extends AppCompatActivity implements NewsFromNewslistAdapter.OnClickReadLaterListener,
    NewsFromNewslistAdapter.OnClickRemoveListener, NewsFromNewslistAdapter.OnClickListener {

    private ListView list;
    private NewsFromNewslistAdapter adapter;
    private NewsList nl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        // Update action bar color
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#06706c")));
        // Get views
        this.list = findViewById(R.id.list_news);
        // Get list
        int list_id = getIntent().getExtras().getInt("list_id");
        try {
            for (NewsList nl1 : NewsListDatabase.getNewsLists(this)) {
                if (nl1.getId() == list_id) {
                    this.nl = nl1;
                }
            }
        } catch (JSONException e) {
            Toast.makeText(this, "List record error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "List record error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        setTitle(getTitle() + " - " + this.nl.getName());
        this.adapter = new NewsFromNewslistAdapter(this, this.nl.getNewslist(),
                this, this, this);
        this.list.setAdapter(this.adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            NewsListDatabase.update(this.nl, this);
        } catch (JSONException e) {
            Toast.makeText(this, "List record error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "List record error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onClickReadLater(int i) {
        News news = this.adapter.getItem(i);
        try {
            if (ReadLaterDatabase.contains(news, this)) {
                ReadLaterDatabase.remove(news, this);
            } else {
                ReadLaterDatabase.add(news, this);
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
    public void onClickRemove(int i) {
        this.nl.remove(this.adapter.getItem(i));
        try {
            NewsListDatabase.update(nl, this);
        } catch (JSONException e) {
            Toast.makeText(this, "List record error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "List record error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int i) {
        News news = this.adapter.getItem(i);
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
