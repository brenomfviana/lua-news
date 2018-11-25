package br.edu.ufrn.brenov.luanews.activities;

import android.os.Bundle;

import br.edu.ufrn.brenov.luanews.R;

public class FavNewspapersActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_newspapers);
        super.onCreateDrawer();
        // Update checked item
        this.navigationView.getMenu().getItem(2).setChecked(true);
    }
}
