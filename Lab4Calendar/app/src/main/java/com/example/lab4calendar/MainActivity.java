package com.example.lab4calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView currentMonthTextView;
    private CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentMonthTextView = findViewById(R.id.current_month_textView); //get month
        compactCalendarView = findViewById(R.id.compactcalendar_view); // get calendar
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY); // default in america is sunday but in europe is monday


        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        long timeInMillis = dateTime.atOffset(ZoneOffset.of("+03:00")).toInstant().toEpochMilli();

        Event ev1 = new Event(Color.GREEN, timeInMillis, "Some data");
        compactCalendarView.addEvent(ev1);

        dateFormatMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
        currentMonthTextView.setText(dateFormatMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Your Channel Name";
            String description = "Your Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channelId", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Request the missing permissions
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        } else {
            // Permission has already been granted, post notification
            postNotification();
            //notificationManager.notify(1, builder.build());
        }


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(java.util.Date dateClicked) {
                Toast toast = Toast.makeText(getApplicationContext(),
                       "Day was clicked: " +  dateClicked.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onMonthScroll(java.util.Date firstDayOfNewMonth) {
                currentMonthTextView.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted, post notification
                postNotification();

            } else {
                // Permission was denied, show a message to the user
                Toast.makeText(this, "Permission denied to post notifications", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void postNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channelId")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Calendar")
                .setContentText("Today is " + LocalDateTime.now().getDayOfMonth())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // Check if the permission is granted before posting the notification
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        } else {
            Toast.makeText(this, "Permission denied to post notifications", Toast.LENGTH_SHORT).show();
        }
    }
}