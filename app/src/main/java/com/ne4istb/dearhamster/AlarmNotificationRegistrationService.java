package com.ne4istb.dearhamster;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmNotificationRegistrationService {

    Context context;

    public AlarmNotificationRegistrationService(Context context) {
        this.context = context;
    }

    public void Register() {

        Intent alarmService = new Intent(context, OnAlarmIntentService.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        setAlarm(0, alarmManager, alarmService, 12);
        setAlarm(1, alarmManager, alarmService, 18);
        setAlarm(2, alarmManager, alarmService, 21);
    }

    private void setAlarm(int id, AlarmManager alarmManager, Intent alarmService, int hour) {

        PendingIntent intent = PendingIntent.getService(context, id, alarmService, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, hour);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, intent);
    }
}
