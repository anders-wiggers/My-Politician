package aacorp.mypolitician.framework;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import aacorp.mypolitician.R;
import aacorp.mypolitician.activities.Settings;
import aacorp.mypolitician.patterns.Database;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = GeofenceBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()){
            Log.e(TAG, String.format("Error code : %d", geofencingEvent.getErrorCode()));
            return;
        }

        int geofenceTransistion = geofencingEvent.getGeofenceTransition();
        if (geofenceTransistion == Geofence.GEOFENCE_TRANSITION_ENTER){
            //TODO Allow only politicians from this region
            //TODO call localPoliticiansOnly
        } else if (geofenceTransistion == Geofence.GEOFENCE_TRANSITION_EXIT){
            //TODO Allow only politicians from different region
        } else {
            Log.e(TAG, String.format("Unknown region - Go to settings and allow all politicians : %d", geofenceTransistion));
            return;
        }
        sendNotification(context, geofenceTransistion);
    }

    private void sendNotification(Context context, int transitionType){
        //TODO find out which class to send notification to
        Intent notificationIntent = new Intent(context, Settings.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(Settings.class);

        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER){
            builder.setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.ic_notifications_black_24dp)).setContentTitle("Entered new region - Learn about new local politicians");
        }

        builder.setContentText("Touch to launch app");
        builder.setContentIntent(notificationPendingIntent);
        builder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, builder.build());
    }


    private void localPoliticiansOnly(){
        //TODO Implement local politicians only
    }

    private void allPoliticians(){
        //TODO implement the ability to swipe all politicians

    }

}
