package br.edu.ufrn.brenov.luanews.view.activities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.sun.syndication.feed.synd.SyndEntry;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.view.fragments.NewsFragment;
import br.edu.ufrn.brenov.luanews.view.notification.NotificationReceiver;
import br.edu.ufrn.brenov.luanews.view.notification.NotificationUtils;

public class HomeActivity extends BaseActivity implements NewsFragment.OnItemClickListener {

    private AlarmManager manager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.onCreateDrawer();
        // Update title
        setTitle("");
        this.navigationView.getMenu().getItem(0).setChecked(true);
        // Create a notification
        Notification.Builder builder = new Notification.Builder(this,
                NotificationUtils.getChannelId(this));
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        // Content
        Notification.BigTextStyle style = new Notification.BigTextStyle()
                .setBigContentTitle("See the read later list.")
                .bigText("You have news to read.");
        builder.setStyle(style);
        // Actions
        Intent intent1 = new Intent(this, NotificationReceiver.class);
        intent1.setAction(NotificationReceiver.ACTION_BUTTON_1);
        PendingIntent pia1 = PendingIntent.getBroadcast(this,1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intent2 = new Intent(this, NotificationReceiver.class);
        intent2.setAction(NotificationReceiver.ACTION_BUTTON_2);
        PendingIntent pia2 = PendingIntent.getBroadcast(this,2, intent2,0);
        Icon icon = Icon.createWithResource(this, android.R.drawable.ic_menu_view);
        Notification.Action action1 = new Notification.Action.Builder(icon,"See now", pia1).build();
        Notification.Action action2 = new Notification.Action.Builder(icon,"Ignore", pia2).build();
        builder.addAction(action1);
        builder.addAction(action2);
        // Show notification
        Notification notification = builder.build();
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(50, notification);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(SyndEntry entry) {
        Intent intent = new Intent(this, NewsActivity.class);
        intent.putExtra("news_link", entry.getLink());
        intent.putExtra("news_title", entry.getTitle());
        intent.putExtra("news_description", entry.getDescription().getValue());
        if (entry.getAuthor().equals("")) {
            intent.putExtra("news_author", "N/A");
        } else {
            intent.putExtra("news_author", entry.getAuthor());
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(entry.getPublishedDate());
        intent.putExtra("news_date", date);
        startActivity(intent);
    }
}
