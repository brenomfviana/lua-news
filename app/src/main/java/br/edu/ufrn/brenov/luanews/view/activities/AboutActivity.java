package br.edu.ufrn.brenov.luanews.view.activities;

import android.os.Bundle;
import br.edu.ufrn.brenov.luanews.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        super.onCreateDrawer();
        // Update checked news_item
        this.navigationView.getMenu().getItem(5).setChecked(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Update checked news_item
        this.navigationView.getMenu().getItem(5).setChecked(true);
    }
}
