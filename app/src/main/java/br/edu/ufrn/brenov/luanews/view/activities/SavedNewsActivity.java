package br.edu.ufrn.brenov.luanews.view.activities;

import android.os.Bundle;

import br.edu.ufrn.brenov.luanews.R;

public class SavedNewsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);
        super.onCreateDrawer();
        // Update checked news_item
        this.navigationView.getMenu().getItem(3).setChecked(true);
    }
}
