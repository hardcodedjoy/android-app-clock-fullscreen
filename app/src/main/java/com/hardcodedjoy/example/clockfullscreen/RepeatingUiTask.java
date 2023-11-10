package com.hardcodedjoy.example.clockfullscreen;

import android.os.Handler;
import android.os.Looper;

public class RepeatingUiTask {

    private final Handler handler;
    private final Runnable taskRunnable;
    private final int intervalMillis;

    public RepeatingUiTask(final Runnable runnable, final int intervalMillis) {
        this.handler = new Handler(Looper.getMainLooper());
        this.intervalMillis = intervalMillis;

        taskRunnable = new Runnable() {
            @Override
            public void run() {
                runnable.run();
                handler.postDelayed(this, intervalMillis);
            }
        };
    }

    public synchronized void start() { handler.postDelayed(taskRunnable, intervalMillis); }
    public synchronized void stop() { handler.removeCallbacks(taskRunnable); }
}