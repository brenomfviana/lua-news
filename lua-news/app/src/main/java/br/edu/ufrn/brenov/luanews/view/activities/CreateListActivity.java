package br.edu.ufrn.brenov.luanews.view.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.controller.database.news.NewsListDatabase;
import br.edu.ufrn.brenov.luanews.model.News;
import br.edu.ufrn.brenov.luanews.model.NewsList;

public class CreateListActivity extends AppCompatActivity {

    private EditText edtListName;
    private int action;
    private int listID;
    private String listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        // Update action bar color
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#06706c")));
        // Get views
        this.edtListName = findViewById(R.id.edt_list_name);
        // Get action
        this.action = getIntent().getIntExtra("action", NewsListDatabase.ACTION_ADD);
        this.listID = getIntent().getIntExtra("list_id", -1);
        this.listName = getIntent().getStringExtra("list_name");
        this.edtListName.setText(this.listName);
        // Check action
        if (this.action == NewsListDatabase.ACTION_UPDATE) {
            Button b = findViewById(R.id.add_list);
            b.setText("Update");
        }
    }

    public void addList(View view) {
        // Create news list
        String name = this.edtListName.getText().toString();
        if (name.equals("")) {
            this.edtListName.setError("Invalid list name!");
        } else {
            switch (this.action) {
                case NewsListDatabase.ACTION_UPDATE:
                    try {
                        NewsList nl = null;
                        for (NewsList n : NewsListDatabase.getNewsLists(this)) {
                            if (n.getId() == listID) {
                                nl = n;
                            }
                        }
                        if (nl == null) {
                            Toast.makeText(this, "The list does not exists.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        nl.setName(name);
                        int result = NewsListDatabase.update(nl, this);
                        switch (result) {
                            case NewsListDatabase.LIST_UPDATED:
                                Toast.makeText(this, "List renamed.", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                                break;
                            default:
                                Toast.makeText(this, "The list could not be renamed.", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (JSONException e) {
                        Toast.makeText(this, "List update error.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (IOException e) {
                        Toast.makeText(this, "List update error.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    break;
                case NewsListDatabase.ACTION_ADD:
                    try {
                        int result = NewsListDatabase.add(name, new ArrayList<News>(), this);
                        switch (result) {
                            case NewsListDatabase.LIST_ADDED:
                                Toast.makeText(this, "List registered.", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                                break;
                            default:
                                Toast.makeText(this, "The list could not be recorded.", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (JSONException e) {
                        Toast.makeText(this, "List record error.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (IOException e) {
                        Toast.makeText(this, "List record error.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
