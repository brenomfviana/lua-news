package br.edu.ufrn.brenov.luanews.view.activities;

import android.os.Bundle;

import br.edu.ufrn.brenov.luanews.R;

public class ReadLaterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_later);
        super.onCreateDrawer();
        // Update checked item
        this.navigationView.getMenu().getItem(4).setChecked(true);
    }
}
