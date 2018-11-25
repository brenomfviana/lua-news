package br.edu.ufrn.brenov.luanews.view.activities;

import android.os.Bundle;

import br.edu.ufrn.brenov.luanews.R;

public class DigitalNewspapersActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_newspapers);
        super.onCreateDrawer();
        // Update checked item
        this.navigationView.getMenu().getItem(1).setChecked(true);
    }
}
