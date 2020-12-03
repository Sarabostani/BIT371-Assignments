package com.example.parallelism;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements LoginDialog.LoginDialogListener {

    ProgressBar pb;
    TextView tv;
    Random random;
    ExecutorService executorService;
    DatePickerDialog datePicker;
    LoginDialog loginDialog;
    TimePickerDialog timePicker;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.progressBar);
        tv = findViewById(R.id.progress_text);
        random = new Random();
        executorService = Executors.newSingleThreadExecutor();

        datePicker = new DatePickerDialog(this);
        datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(
                        getApplicationContext(),
                        String.format("%02d/%02d/%d", month, dayOfMonth, year), Toast.LENGTH_LONG).show();
            }
        });

        timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(
                        getApplicationContext(),
                        String.format("%02d:%02d", hourOfDay, minute), Toast.LENGTH_LONG).show();
            }
        }, 0, 0, false);

        loginDialog = new LoginDialog();
        loginDialog.setCancelable(false);
    }

    public void basicCall(View view) {
        doFakeWork();
    }

    public void useThread(View view) {
        // Spawn a new thread
        new Thread(() -> {
            doFakeWork();
        }).start();
    }

    /*
    Dialogs
     */
    public void pickDate(View view) {
        datePicker.show();
    }

    public void pickTime(View view) {
        timePicker.show();
    }

    public void showDialog(View view) {
        loginDialog.show(this.getSupportFragmentManager(), "Login");
    }

    public void useHandlerThread(View view) {
        final int SOME_MESSAGE_ID = 0;
        HandlerThread thread = new HandlerThread("ProgressThread");
        thread.start();

        Looper looper = thread.getLooper();
        Handler handler = new Handler(looper) {
            // Created an anonymous class which extends Handler
            // Overriding handleMessage for handling incoming messages in a specific way
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case SOME_MESSAGE_ID:
                        // SOME_MESSAGE_ID is any int value
                        // do something
                        break;
                    // other cases
                }
            }
        };

        handler.post(() -> doFakeWork());

        // Handler.handleMessage() will be executed on the created thread
        // after the previous Runnable is finished
        handler.sendEmptyMessage(SOME_MESSAGE_ID);
    }

    public void useExecutorService(View view) {

        System.out.println("Creating a Runnable...");
        Runnable runnable = () -> {
            String threadName = Thread.currentThread().getName();
            long currentMillis = System.currentTimeMillis();
            System.out.println("Inside : " + threadName);
            doFakeWork();
            System.out.println(String.format("Thread %s is done after %d milli seconds!", threadName, System.currentTimeMillis() - currentMillis));
        };

        System.out.println("Submit the task specified by the runnable to the executor service.");
        executorService.submit(runnable);
    }


    /*
    USING ASYNCTASK -- THANKFULLY DEPRECATED
     */

    public void useAsyncTask(View view) {
        new SingleTask().execute(new String[] {"First Task"});
    }

    @Override
    public void storeToken(String token) {
        System.out.println(String.format("Token: %s", token));
    }

    private class SingleTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            doFakeWork();
            return String.format("Task \"%s\" is done!", strings[0]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println(s);
        }
    }



    /// MOCKING FETCHERS
    // Simulating something timeconsuming
    private void doFakeWork() {
        while(fetchValue()) {
            System.out.println("Making Progress...");
        }
    }
    private boolean fetchValue() {
        SystemClock.sleep(1000 + random.nextInt(4000));
        setPb(1 + random.nextInt(2));
        return pb.getProgress() < 10;
    }

    private void setPb(int val) {
        int progress = val + pb.getProgress();
        pb.setProgress(progress >= 10 ? 10 : progress);
    }
}