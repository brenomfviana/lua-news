package br.edu.ufrn.brenov.luanews;

import android.os.Bundle;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.onCreateDrawer();
        // Update title
        setTitle("");
        this.navigationView.getMenu().getItem(0).setChecked(true);
    }
}
