package br.edu.ufrn.brenov.luanews.view.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.view.adapters.NewsAdapter;
import br.edu.ufrn.brenov.luanews.view.fragments.NewsFragment;
import br.edu.ufrn.brenov.luanews.view.notification.NotificationReceiver;
import br.edu.ufrn.brenov.luanews.view.notification.NotificationUtils;

public class HomeActivity extends BaseActivity implements NewsAdapter.OnClickReadLaterListener,
        NewsAdapter.OnClickListener {

    private EditText edtSearch;
    private OnTyppingListener typpingListener;
    private OnClickReadLaterListener readLaterListener;
    private OnClickListener listener;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.onCreateDrawer();
        // Update title
        setTitle("");
        this.navigationView.getMenu().getItem(0).setChecked(true);
        // Get views
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.home_fragment);
        this.typpingListener = (NewsFragment) frag;
        this.readLaterListener = (NewsFragment) frag;
        this.listener = (NewsFragment) frag;
        this.edtSearch = findViewById(R.id.edt_search);
        this.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Empty
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                typpingListener.onTypping(edtSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Empty
            }
        });
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
    public void onClickReadLater(int i) {
        this.readLaterListener.onClickReadLater(i);
    }

    @Override
    public void onClick(int i) {
        this.listener.onClick(i);
    }

    public interface OnTyppingListener {
        void onTypping(String text);
    }

    public interface OnClickReadLaterListener {
        void onClickReadLater(int i);
    }

    public interface OnClickListener {
        void onClick(int i);
    }
}
