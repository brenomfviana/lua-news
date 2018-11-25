package br.edu.ufrn.brenov.luanews;

import android.os.Bundle;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        super.onCreateDrawer();
        // Update checked item
        this.navigationView.getMenu().getItem(5).setChecked(true);
    }
}
