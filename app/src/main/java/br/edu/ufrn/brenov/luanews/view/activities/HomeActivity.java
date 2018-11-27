package br.edu.ufrn.brenov.luanews.view.activities;

import android.content.Intent;
import android.os.Bundle;
import com.sun.syndication.feed.synd.SyndEntry;
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
        intent.putExtra("news_title", entry.getTitle());
        startActivity(intent);
    }
}
