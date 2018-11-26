package br.edu.ufrn.brenov.luanews.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.edu.ufrn.brenov.luanews.R;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        String str = getIntent().getExtras().getString("news_title");
        TextView textView = findViewById(R.id.textView);
        textView.setText(str);
    }
}
