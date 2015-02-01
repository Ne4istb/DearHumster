package com.ne4istb.dearhamster;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

public class OnAlarmIntentService extends IntentService {

    public static final String ALARM_INTENT_SERVICE = "OnAlarmIntentService";

    public OnAlarmIntentService() {
        super(ALARM_INTENT_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        processAlarm();
        registerNextAlarm();
    }

    private void processAlarm() {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext());

        Intent intent = new Intent(this, ImageActivity.class);

        String category = getRandomCategory();
        intent.putExtra(CategoryFragment.ARG_CATEGORY_NAME, category);
        intent.putExtra(CategoryFragment.ARG_IMAGE_POSITION, getRandomImagePosition(category));

        PendingIntent openActivityIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        if (calendar.get(Calendar.HOUR_OF_DAY) > 11) {
            notificationBuilder.setVibrate(new long[]{500, 500}).setSound(alarmSound);
        }

        notificationBuilder
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.love_you))
                .setContentText(getString(R.string.NotificationContent))
                .setContentIntent(openActivityIntent)
                .setAutoCancel(true);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, notificationBuilder.build());

    }

    private int getRandomImagePosition(String category) {

        AssetManager assetManager = this.getAssets();

        try {
            String[] list = assetManager.list(category);
            return randomInt(list.length);
        } catch (IOException e) {
            return 0;
        }
    }

    private String getRandomCategory() {

        int categoryId = randomInt(3);

        switch (categoryId) {
            case 1:
                return CategoriesActivity.BEASTS_CATEGORY;
            case 2:
                return CategoriesActivity.MINIONS_CATEGORY;
            default:
                return CategoriesActivity.WEDDING_CATEGORY;
        }
    }

    private void registerNextAlarm() {

        AlarmNotificationService alarmService = new AlarmNotificationService(this);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.SECOND, 0);

        int  targetMinute = randomInt(59);
        calendar.set(Calendar.MINUTE, targetMinute);

        int targetHour = calendar.get(Calendar.HOUR_OF_DAY) + randomInt(2, 6);

        if (targetHour <= 24)
            calendar.set(Calendar.HOUR_OF_DAY, targetHour);
        else
        {
            calendar.set(Calendar.HOUR_OF_DAY, targetHour - 24);
            int targetDayOfYear = calendar.get(Calendar.DAY_OF_YEAR) + 1;

            if (targetDayOfYear <= 365)
                calendar.set(Calendar.DAY_OF_YEAR, targetDayOfYear);
            else
                calendar.set(Calendar.DAY_OF_YEAR, 1);
        }

        alarmService.setAlarm(0, calendar.getTimeInMillis());
    }

    private int randomInt(int max) {
        return new Random().nextInt(max);
    }

    private int randomInt(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}




















