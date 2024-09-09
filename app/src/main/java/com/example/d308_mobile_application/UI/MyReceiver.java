package com.example.d308_mobile_application.UI;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308_mobile_application.R;

//Task B3
//Changes: Included CRUD, Notify, Validation, and share features   | Files: VacationDetails.java,
//myReceiver.java, menu_vacation_details.xml    | lines: menu_vacation_details = 320-356 /
//myReceiver.java = All lines / VacationDetails.java = notify: 320-356, Share: 357 - 381 / Validation = 97-138

public class MyReceiver extends BroadcastReceiver {

    String channel_id = "test";
    static int notificationID;


    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra("notifyExcursion")){
            Toast.makeText(context, intent.getStringExtra("notifyExcursion"), Toast.LENGTH_LONG).show();
        }
        if (intent.hasExtra("notifyVacationStart")){
            Toast.makeText(context, intent.getStringExtra("notifyVacationStart"), Toast.LENGTH_LONG).show();
        }
        if (intent.hasExtra("notifyVacationEnd")){
            Toast.makeText(context, intent.getStringExtra("notifyVacationEnd"), Toast.LENGTH_LONG).show();
        }


        createNotificationChannel(context, channel_id);
        Notification n = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("notifyVacationStart"))
                .setContentTitle("Vacation Notification").build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, n);
    }



    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}