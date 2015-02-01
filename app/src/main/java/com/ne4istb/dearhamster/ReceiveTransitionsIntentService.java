//package com.ne4istb.dearhamster;
//
//import android.app.IntentService;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//import android.widget.ImageView;
//
//import com.google.android.gms.location.Geofence;
//import com.google.android.gms.location.LocationClient;
//import com.ne4istb.dearhamster.GeofencingService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReceiveTransitionsIntentService extends IntentService {
//
//    public static final String TRANSITION_INTENT_SERVICE = "com.ne4istb.dearhamster.ReceiveTransitionsIntentService";
//
//    public ReceiveTransitionsIntentService() {
//        super(TRANSITION_INTENT_SERVICE);
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//
//        if (LocationClient.hasError(intent)) {
//            Log.e(TRANSITION_INTENT_SERVICE, "Location Services error: " + LocationClient.getErrorCode(intent));
//            return;
//        }
//
//        int transitionType = LocationClient.getGeofenceTransition(intent);
//
//        List<Geofence> triggeredGeofences = LocationClient.getTriggeringGeofences(intent);
//        List<String> triggeredIds = new ArrayList<String>();
//
//        for (Geofence geofence : triggeredGeofences) {
//            Log.d("GEO", "onHandle:" + geofence.getRequestId());
//            processGeofence(geofence, transitionType);
//            triggeredIds.add(geofence.getRequestId());
//        }
//
//        if (transitionType == Geofence.GEOFENCE_TRANSITION_EXIT)
//            removeGeofences(triggeredIds);
//    }
//
//    private void processGeofence(Geofence geofence, int transitionType) {
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext());
//
//        PendingIntent openActivityIntetnt = PendingIntent.getActivity(this, 0, new Intent(this, ImageActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
//        int id = Integer.parseInt(geofence.getRequestId());
//
//        notificationBuilder
//                .setSmallIcon(R.drawable.ic_launcher)
//                .setContentTitle("Geofence id: " + id)
//                .setContentText("Transition type: " + getTransitionTypeString(transitionType))
//                .setVibrate(new long[]{500, 500})
//                .setContentIntent(openActivityIntetnt)
//                .setAutoCancel(true);
//
//        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        nm.notify(transitionType * 100 + id, notificationBuilder.build());
//
//        Log.d("GEO", "notification built:" + id);
//    }
//
//    private String getTransitionTypeString(int transitionType) {
//        switch (transitionType) {
//            case Geofence.GEOFENCE_TRANSITION_ENTER:
//                return "enter";
//            case Geofence.GEOFENCE_TRANSITION_EXIT:
//                return "exit";
//            case Geofence.GEOFENCE_TRANSITION_DWELL:
//                return "dwell";
//            default:
//                return "unknown";
//        }
//    }
//
//    private void removeGeofences(List<String> requestIds) {
//        Intent intent = new Intent(getApplicationContext(), GeofencingService.class);
//
//        String[] ids = new String[0];
//        intent.putExtra(GeofencingService.EXTRA_REQUEST_IDS, requestIds.toArray(ids));
//        intent.putExtra(GeofencingService.EXTRA_ACTION, GeofencingService.Action.REMOVE);
//
//        startService(intent);
//    }
//}