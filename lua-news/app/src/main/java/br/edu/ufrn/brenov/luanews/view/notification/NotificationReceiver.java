package br.edu.ufrn.brenov.luanews.view.notification;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import br.edu.ufrn.brenov.luanews.view.activities.ReadLaterActivity;

public class NotificationReceiver extends BroadcastReceiver {

    public static final String ACTION_BUTTON_1 = "br.edu.ufrn.brenov.luanews.ACTION_BUTTON_1";
    public static final String ACTION_BUTTON_2 = "br.edu.ufrn.brenov.luanews.ACTION_BUTTON_2";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get actions
        String action = intent.getAction();
        switch (action) {
            case ACTION_BUTTON_1:
                Intent i = new Intent(context, ReadLaterActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(i);
                break;
            case ACTION_BUTTON_2:
                break;
            default:
                break;
        }
        NotificationManager nm = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(50);
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }
}