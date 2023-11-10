package com.hardcodedjoy.example.clockfullscreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    private SimpleDateFormat timeFormat;
    private SimpleDateFormat dayFormat;
    private SimpleDateFormat dateFormat;
    private RepeatingUiTask timeUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGUI();
    }

    private void initGUI() {
        goFullscreen();
        setKeepScreenOn();
        setContentView(R.layout.layout_main);

        TextView tvTime = findViewById(R.id.tv_time);
        TextView tvDay = findViewById(R.id.tv_day);
        TextView tvDate = findViewById(R.id.tv_date);

        timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        dayFormat  = new SimpleDateFormat("EEEE", Locale.US);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        timeUpdater = new RepeatingUiTask(() -> {
            Date date = new Date();
            tvTime.setText(timeFormat.format(date));
            tvDay.setText(dayFormat.format(date));
            tvDate.setText(dateFormat.format(date));
        }, 50);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timeUpdater.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timeUpdater.stop();
    }

    private void goFullscreen() {
        Window window = getWindow();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // extra-fullscreen: also cover camera hole -> see file styles.xml (v27)

        // extra-fullscreen: also cover navigation buttons
        // they will reappear when swiped from the bottom of the screen
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        // alternatively, to extend the app GUI and also draw the navigation buttons on top:
        // window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void setKeepScreenOn() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}