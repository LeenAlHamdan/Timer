package com.leenalhamdan.timer.view.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.leenalhamdan.timer.R;
import com.leenalhamdan.timer.model.entity.Timer;
import com.leenalhamdan.timer.view.dialogs.DialogAddAndEditDurations;
import com.leenalhamdan.timer.viewmodel.ViewModelMotivationalQuote;
import com.leenalhamdan.timer.viewmodel.ViewModelTimer;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
{
    private ImageView imgChangeIntervals;

    private ConstraintLayout layoutMainActivity;

    private Button btnResume;
    private Button btnPause;
    private Button btnStart;

    private TextView txtHeadingTitle;
    private TextView txtRoundNumber;
    private TextView txtTimerCounter;
    private TextView txtRoundType;

    private ViewModelTimer viewModelTimer;
    private ViewModelMotivationalQuote viewModelMotivationalQuote;

    private Timer timer;

    private int round = 1;

    private boolean isBreakTime = false;

    private CountDownTimer countDownTimer;

    private long millisRemaining;

    private boolean mTimerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(getApplicationContext());

        imgChangeIntervals = findViewById(R.id.imgChangeIntervals);
        btnResume = findViewById(R.id.btnResume);
        btnPause = findViewById(R.id.btnPause);
        btnStart = findViewById(R.id.btnStart);

        txtHeadingTitle = findViewById(R.id.txtHeadingTitle);
        txtRoundNumber = findViewById(R.id.txtRoundNumber);
        txtTimerCounter = findViewById(R.id.txtTimerCounter);
        txtRoundType = findViewById(R.id.txtRoundType);

        layoutMainActivity = findViewById(R.id.layoutMainActivity);

        viewModelTimer = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelTimer.class);
        viewModelMotivationalQuote = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelMotivationalQuote.class);

        viewModelMotivationalQuote.downloadMotivationalQuote();

        viewModelMotivationalQuote.getMotivationalQuote().observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtHeadingTitle.setText(s);

            }
        });

        //to make sure that user choose his interval.
        if (!Paper.book().contains("#HasTimer")) {
            DialogAddAndEditDurations dialogAddAndEditDurations = new DialogAddAndEditDurations(true);
            dialogAddAndEditDurations.setCancelable(false);
            dialogAddAndEditDurations.show(getSupportFragmentManager(), "DialogAddVoltageSource");
        } else {
            viewModelTimer.getTimer().observe(MainActivity.this, new Observer<Timer>() {
                @Override
                public void onChanged(Timer timer) {
                    MainActivity.this.timer = timer;
                    Paper.book().write("Timer", timer);
                    btnStart.setVisibility(View.VISIBLE);
                    if (mTimerRunning) {
                        countDownTimer.cancel();
                        txtTimerCounter.setText("00:00:00");
                        round=1;
                        isBreakTime=false;
                    }
                }
            });


            btnStart.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    timer=Paper.book().read("Timer",null);
                    if(mTimerRunning)
                        countDownTimer.cancel();
                    txtRoundType.setText("Training will start in:");
                    txtTimerCounter.setTextColor(Color.parseColor("#FF0000"));
                    countDownTimer = new CountDownTimer(3000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            MainActivity.this.millisRemaining = millisUntilFinished;
                            updateCountDownText();
                        }

                        @Override
                        public void onFinish() {
                            txtTimerCounter.setText("00:00:00");
                            txtTimerCounter.setTextColor(Color.parseColor("#FFFFFF"));
                            btnStart.setVisibility(View.GONE);
                            startTimer(timer);
                        }
                    }.start();
                }
            });

            //change the interval
            imgChangeIntervals.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogAddAndEditDurations dialogAddAndEditDurations = new DialogAddAndEditDurations(timer);
                    dialogAddAndEditDurations.show(getSupportFragmentManager(), "DialogAddVoltageSource");
                }
            });

            btnPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTimerRunning) {
                        pauseTimer();
                    }
                }
            });

            btnResume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mTimerRunning)
                        resumeTimer();
                }
            });
        }
    }

    private void startTimer(Timer timer) {
        MainActivity.this.timer = timer;
        if (round <= timer.getNumberOfTrainingIntervals()) {
            txtRoundNumber.setText("Round " + round);
            if (!isBreakTime) {
                txtRoundType.setText("Training");
                layoutMainActivity.setBackground(getResources().getDrawable(R.drawable.background_dark_theme));
                millisRemaining = timer.getDurationOfTrainingIntervals() * 1000;
                countDownTimer = new CountDownTimer(millisRemaining, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        MainActivity.this.millisRemaining = millisUntilFinished;
                        updateCountDownText();
                    }

                    @Override
                    public void onFinish() {
                        txtTimerCounter.setText("00:00:00");
                        isBreakTime = !isBreakTime;
                        startTimer(timer);
                    }
                }.start();
                mTimerRunning = true;
            } else {
                txtRoundType.setText("Break Time");
                layoutMainActivity.setBackground(getResources().getDrawable(R.drawable.background_break));
                millisRemaining = timer.getDurationOfBreakIntervals() * 1000;
                countDownTimer = new CountDownTimer(millisRemaining, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        MainActivity.this.millisRemaining = millisUntilFinished;
                        updateCountDownText();
                    }

                    @Override
                    public void onFinish() {
                        txtTimerCounter.setText("00:00:00");
                        isBreakTime = !isBreakTime;
                        round++;
                        startTimer(timer);
                    }

                }.start();
                mTimerRunning = true;
            }
        }
        else
        {
            round=1;
            txtRoundType.setText("");
            txtRoundNumber.setText("Round"+round);
            btnStart.setVisibility(View.VISIBLE);
            return;
        }
    }

    private void resumeTimer() {
        mTimerRunning = true;
        countDownTimer = new CountDownTimer(millisRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                MainActivity.this.millisRemaining = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                txtTimerCounter.setText("00:00:00");
                isBreakTime = !isBreakTime;
                startTimer(timer);
            }
        }.start();
    }


    private void pauseTimer() {
        countDownTimer.cancel();
        mTimerRunning = false;
    }

    private void updateCountDownText() {
        NumberFormat f = new DecimalFormat("00");

        long hour = (millisRemaining / 3600000) % 24;
        long min = (millisRemaining / 60000) % 60;
        long sec = (millisRemaining / 1000) % 60;
        txtTimerCounter.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
    }

}