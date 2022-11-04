package com.raisa.update1.object;

import android.content.Intent;
import android.provider.AlarmClock;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class Alarm extends AppCompatActivity {
    String title;
    String timeHour;
    String timeMinute;
    String everyDay;
    String Sunday;
    String Monday;
    String Tuesday;
    String WednesDay;
    String Thursday;
    String Friday;
    String Saturday;
    final ArrayList<Integer> daysOfWeek= new ArrayList<>();

    public Alarm(String timeHour, String timeMinute, String everyDay,
                 String sunday, String monday, String tuesday,
                 String wednesDay, String thursday, String friday,
                 String saturday, String title) {
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.everyDay = everyDay;
        this.title = title;
        Sunday = sunday;
        Monday = monday;
        Tuesday = tuesday;
        WednesDay = wednesDay;
        Thursday = thursday;
        Friday = friday;
        Saturday = saturday;
    }


public void SetAlarm()
{
//    if(everyDay == "1")
//    {
//        daysOfWeek.add(Calendar.SATURDAY);
//        daysOfWeek.add(Calendar.SUNDAY);
//        daysOfWeek.add(Calendar.MONDAY);
//        daysOfWeek.add(Calendar.TUESDAY);
//        daysOfWeek.add(Calendar.WEDNESDAY);
//        daysOfWeek.add(Calendar.THURSDAY);
//        daysOfWeek.add(Calendar.FRIDAY);
//    }
//    if (Saturday == "1")
//    {
//        daysOfWeek.add(Calendar.SATURDAY);
//    }
//    if (Sunday=="1")
//    {
//        daysOfWeek.add(Calendar.SUNDAY);
//    }
//    if (Monday == "1")
//    {
//        daysOfWeek.add(Calendar.MONDAY);
//    }
//    if (Tuesday== "1")
//    {
//        daysOfWeek.add(Calendar.TUESDAY);
//    }
//    if (WednesDay == "1")
//    {
//        daysOfWeek.add(Calendar.WEDNESDAY);
//    }
//    if (Thursday == "1")
//    {
//        daysOfWeek.add(Calendar.THURSDAY);
//    }
//    if (Friday == "1")
//    {
//        daysOfWeek.add(Calendar.FRIDAY);
//    }
    daysOfWeek.add(Calendar.SUNDAY);
    daysOfWeek.add(Calendar.MONDAY);
    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
    intent.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(timeHour));
    intent.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(timeMinute));
    intent.putExtra(AlarmClock.EXTRA_MESSAGE, title);
    intent.putExtra(AlarmClock.EXTRA_DAYS, daysOfWeek);
    intent.putExtra(AlarmClock.EXTRA_VIBRATE, false);
    intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
    intent.putExtra(AlarmClock.EXTRA_RINGTONE, true);
    if(intent.resolveActivity(getPackageManager()) != null){
        startActivity(intent);
    }else{

    }
}





}
