package br.edu.ufrn.brenov.luanews.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.util.List;
import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.controller.database.news.NewsListDatabase;
import br.edu.ufrn.brenov.luanews.model.NewsList;
import br.edu.ufrn.brenov.luanews.view.adapters.NewsListAdapter;

public class SavedNewsActivity extends BaseActivity implements NewsListAdapter.OnClickEditListener,
        NewsListAdapter.OnClickRemoveListener, NewsListAdapter.OnClickListener {

    private ListView list;
    private List<NewsList> nls;
    private NewsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);
        super.onCreateDrawer();
        // Update checked news_item
        this.navigationView.getMenu().getItem(3).setChecked(true);
        // Get views
        this.list = findViewById(R.id.list_of_newspapers_list);
        // Get lists
        try {
            this.nls = NewsListDatabase.getNewsLists(this);
        } catch (JSONException e) {
            Toast.makeText(this, "Error while reading News Lists.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "Error while reading News Lists.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        this.adapter = new NewsListAdapter(this, this.nls, this, this, this);
        this.list.setAdapter(this.adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Get news
        try {
            this.nls = NewsListDatabase.getNewsLists(this);
        } catch (JSONException e) {
            Toast.makeText(this, "Error while reading RSS feeds.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "Error while reading RSS feeds.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        this.adapter = new NewsListAdapter(this, this.nls, this, this, this);
        this.list.setAdapter(this.adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.general_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Intent intent = new Intent(this, CreateListActivity.class);
                intent.putExtra("action", NewsListDatabase.ACTION_ADD);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickEdit(int i) {
        Intent intent = new Intent(this, CreateListActivity.class);
        intent.putExtra("action", NewsListDatabase.ACTION_UPDATE);
        intent.putExtra("list_id", this.nls.get(i).getId());
        intent.putExtra("list_name", this.nls.get(i).getName());
        startActivity(intent);
    }

    @Override
    public void onClickRemove(int i) {
        try {
            int result = NewsListDatabase.remove(this.nls.get(i), this);
            if (result == NewsListDatabase.LIST_REMOVED) {
                this.nls.remove(i);
                this.adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Error while removing RSS feeds.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "Error while removing RSS feeds.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(int i) {
        Intent intent = new Intent(this, NewsListActivity.class);
        intent.putExtra("list_id", this.adapter.getItem(i).getId());
        startActivity(intent);
    }
}
