package com.ne4istb.dearhamster;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmNotificationService {

    Context context;

    public AlarmNotificationService(Context context) {
        this.context = context;
    }

    public void Register() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 21);

        setAlarm(0, calendar.getTimeInMillis());
    }

    public void setAlarm(int id, long timeInMillis) {

        Intent alarmService = new Intent(context, OnAlarmIntentService.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        PendingIntent intent = PendingIntent.getService(context, id, alarmService, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, intent);
    }
}
