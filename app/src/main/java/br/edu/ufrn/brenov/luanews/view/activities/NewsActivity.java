package br.edu.ufrn.brenov.luanews.view.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.model.News;
import br.edu.ufrn.brenov.luanews.view.dialogs.AddToListDialog;

public class NewsActivity extends AppCompatActivity {

    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        // Update action bar color
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#06706c")));
        // Get news title
        String str1 = getIntent().getExtras().getString("news_title");
        TextView textTitle = findViewById(R.id.news_title);
        textTitle.setText(str1);
        // Get news author
        String str2 = getIntent().getExtras().getString("news_author");
        TextView textAuthor = findViewById(R.id.news_author);
        textAuthor.setText(str2);
        // Get news date
        String str3 = getIntent().getExtras().getString("news_date");
        TextView textDate = findViewById(R.id.news_date);
        textDate.setText(str3);
        // Get news description
        String str4 = getIntent().getExtras().getString("news_description");
        TextView textDescription = findViewById(R.id.news_description);
        textDescription.setText(str4);
        // Set link
        Button open = findViewById(R.id.open_link);
        final String link = getIntent().getExtras().getString("news_link");
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(i);
            }
        });
        // Create news
        this.news = new News(link, str1, str2, str4, str3);
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
                AddToListDialog.show(getSupportFragmentManager(), this, this.news);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
