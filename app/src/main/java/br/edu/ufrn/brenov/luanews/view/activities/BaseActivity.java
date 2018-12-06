package br.edu.ufrn.brenov.luanews.view.activities;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ufrn.brenov.luanews.R;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected Toolbar toolbar;
    protected DrawerLayout drawer;
    protected NavigationView navigationView;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        onCreateDrawer() ;
    }

    protected void onCreateDrawer() {
        // Get toolbar
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get navigation drawer
        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // Show navigation drawer
        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view news_item clicks here.
        if (!item.isChecked()) {
            int id = item.getItemId();
            Intent intent = null;
            // Check selected news_item
            if (id == R.id.nav_home) {
                intent = new Intent(this, HomeActivity.class);
            } else if (id == R.id.nav_newspapers) {
                intent = new Intent(this, DigitalNewspapersActivity.class);
            } else if (id == R.id.nav_fav_newspapers) {
                intent = new Intent(this, FavNewspapersActivity.class);
            } else if (id == R.id.nav_saved_news) {
                intent = new Intent(this, SavedNewsActivity.class);
            } else if (id == R.id.nav_read_later) {
                intent = new Intent(this, ReadLaterActivity.class);
            } else if (id == R.id.nav_about) {
                intent = new Intent(this, AboutActivity.class);
            } else if (id == R.id.nav_logoff) {
                // Remove file of login
                intent = new Intent(this, MainActivity.class);
            }
            startActivity(intent);
        }
        // Close drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
