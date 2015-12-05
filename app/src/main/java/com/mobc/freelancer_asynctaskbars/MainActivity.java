package com.mobc.freelancer_asynctaskbars;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

    public static final String TAG = "DEBUGGING";
    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private EditText numberField;
    private ProgressBar bar1;
    private ProgressBar bar2;
    private ProgressBar bar3;
    private ProgressBar bar4;
    private ProgressBar bar5;
    private ProgressBar bar6;
    private TextView counterView;
    private int tempCounter = 0;
    private int toCount = 0;
    private int CANCEL_FLAG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        setListeners();

    }


    // Initializing all UI conponents
    public void initUI() {
        startButton = (Button) findViewById(R.id.start_button);
        stopButton = (Button) findViewById(R.id.stop_button);
        resetButton = (Button) findViewById(R.id.reset_button);
        numberField = (EditText) findViewById(R.id.number_field);

        bar1 = (ProgressBar) findViewById(R.id.pbar1);
        bar2 = (ProgressBar) findViewById(R.id.pbar2);
        bar3 = (ProgressBar) findViewById(R.id.pbar3);
        bar4 = (ProgressBar) findViewById(R.id.pbar4);
        bar5 = (ProgressBar) findViewById(R.id.pbar5);
        bar6 = (ProgressBar) findViewById(R.id.pbar6);
        counterView = (TextView) findViewById(R.id.counter);

    }


    // Setting Onclick listeners
    public void setListeners() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAction();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAction();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAction();
            }
        });
    }


    // When "Start" button is clicked
    public void startAction() {
        CANCEL_FLAG = 0;
        if (!String.valueOf(numberField.getText()).equals("")) {
            toCount = Integer.valueOf(String.valueOf(numberField.getText()));
            if (toCount > 600 || toCount < 0) {
                Toast.makeText(this, "Please enter a number between 0 and 600", Toast.LENGTH_LONG).show();
            } else {
                // If the number is correct
                resetAction();

                for (int i = 0; i < 6; i++) {
                    new AsyncCounter().execute(toCount);
                }

            }
        } else {
            Toast.makeText(this, "Please enter a number between 0 and 600", Toast.LENGTH_LONG).show();
        }
    }


    // When "Stop" button is clicked
    public void stopAction() {
        CANCEL_FLAG = 1;
    }


    // When "Reset" button is clicked
    public void resetAction() {
        bar1.setProgress(0);
        bar2.setProgress(0);
        bar3.setProgress(0);
        bar4.setProgress(0);
        bar5.setProgress(0);
        bar6.setProgress(0);
        counterView.setText("0");
        tempCounter = 0;
    }


    // Asynchronous task to count
    class AsyncCounter extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... params) {
            for (int i = 0; i < 100; i++) {
                if (CANCEL_FLAG == 0) {
                    if (tempCounter != toCount) {
                        tempCounter++;
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                counterView.setText(String.valueOf(tempCounter));
                                if (tempCounter < 101) {
                                    bar1.setProgress(tempCounter);
                                } else if (tempCounter < 201) {
                                    bar2.setProgress(tempCounter-100);
                                } else if (tempCounter < 301) {
                                    bar3.setProgress(tempCounter-200);
                                } else if (tempCounter < 401) {
                                    bar4.setProgress(tempCounter-300);
                                } else if (tempCounter < 501) {
                                    bar5.setProgress(tempCounter-400);
                                } else if (tempCounter < 601) {
                                    bar6.setProgress(tempCounter-500);
                                }
                            }
                        });
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    this.cancel(true);
                }
            }
            return null;
        }
    }

}
