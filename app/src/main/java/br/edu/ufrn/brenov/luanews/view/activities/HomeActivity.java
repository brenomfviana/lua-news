package br.edu.ufrn.brenov.luanews.view.activities;

import android.content.Intent;
import android.os.Bundle;
import com.sun.syndication.feed.synd.SyndEntry;
import java.text.SimpleDateFormat;
import br.edu.ufrn.brenov.luanews.R;
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
    }

    @Override
    public void onClick(SyndEntry entry) {
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
