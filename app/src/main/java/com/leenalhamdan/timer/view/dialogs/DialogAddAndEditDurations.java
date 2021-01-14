package com.leenalhamdan.timer.view.dialogs;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.ikovac.timepickerwithseconds.MyTimePickerDialog;
import com.ikovac.timepickerwithseconds.TimePicker;
import com.leenalhamdan.timer.R;
import com.leenalhamdan.timer.model.entity.Timer;
import com.leenalhamdan.timer.viewmodel.ViewModelTimer;

import java.util.Calendar;

import io.paperdb.Paper;

public class DialogAddAndEditDurations extends DialogFragment {
    private Button btnDialogAddAndEditDurationsCancel;
    private Button btnDialogAddAndEditDurationsSave;

    private TextView txtDialogAddAndEditDurationsBreak;
    private TextView txtDialogAddAndEditDurationsTraining;

    private TextInputEditText edtDialogAddAndEditNumberOfTrainingIntervals;
    private TextView txtDialogAddAndEditDurationsTitle;

    private boolean isAdding;

    private Timer timer;

    private ViewModelTimer viewModelTimer;

    public DialogAddAndEditDurations(boolean isAdding) {
        this.isAdding = isAdding;
    }

    public DialogAddAndEditDurations(Timer timer) {
        this.timer = timer;
        isAdding = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_and_edit_durations, container, false);

        Paper.init(getContext());

        btnDialogAddAndEditDurationsCancel = view.findViewById(R.id.btnDialogAddAndEditDurationsCancel);
        btnDialogAddAndEditDurationsSave = view.findViewById(R.id.btnDialogAddAndEditDurationsSave);

        txtDialogAddAndEditDurationsBreak = view.findViewById(R.id.txtDialogAddAndEditDurationsBreak);
        txtDialogAddAndEditDurationsTraining = view.findViewById(R.id.txtDialogAddAndEditDurationsTraining);
        edtDialogAddAndEditNumberOfTrainingIntervals = view.findViewById(R.id.edtDialogAddAndEditNumberOfTrainingIntervals);
        txtDialogAddAndEditDurationsTitle = view.findViewById(R.id.txtDialogAddAndEditDurationsTitle);

        viewModelTimer = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(ViewModelTimer.class);

        if (!isAdding) //if is editing ,show the data
        {
            txtDialogAddAndEditDurationsTitle.setText("Edit durations");//set title of dialog
            btnDialogAddAndEditDurationsCancel.setVisibility(View.VISIBLE); //can cancel

            edtDialogAddAndEditNumberOfTrainingIntervals.setText(timer.getNumberOfTrainingIntervals() + "");

            long trainingHoursCount = timer.getDurationOfTrainingIntervals() / (60 * 60); //number of hours in duration of training intervals
            long trainingMinutesCount = timer.getDurationOfTrainingIntervals() / 60 - (trainingHoursCount * 60 * 60); //number of minutes in duration of training intervals
            long trainingSecondsCount = timer.getDurationOfTrainingIntervals() - (trainingMinutesCount * 60); //number of seconds in duration of training intervals
            txtDialogAddAndEditDurationsTraining.setText(trainingHoursCount + ":" + trainingMinutesCount + ":" + trainingSecondsCount);

            long breakHoursCount = timer.getDurationOfBreakIntervals() / (60 * 60); //number of hours in duration of break intervals
            long breakMinutesCount = timer.getDurationOfBreakIntervals() / 60 - (breakHoursCount * 60 * 60); //number of minutes in duration of break intervals
            long breakSecondsCount = timer.getDurationOfBreakIntervals() - (breakMinutesCount * 60); //number of seconds in duration of break intervals
            txtDialogAddAndEditDurationsBreak.setText(breakHoursCount + ":" + breakMinutesCount + ":" + breakSecondsCount);
        } else {
            txtDialogAddAndEditDurationsTitle.setText("Add durations"); //set title of dialog
            btnDialogAddAndEditDurationsCancel.setVisibility(View.GONE); //force him to add his durations

        }

        txtDialogAddAndEditDurationsTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar start = Calendar.getInstance();

                if (!isAdding) {
                    long hoursCount = timer.getDurationOfTrainingIntervals() / (60 * 60); //number of hours in duration of training intervals
                    long minutesCount = timer.getDurationOfTrainingIntervals() / 60 - (hoursCount * 60 * 60); //number of minutes in duration of training intervals
                    long secondsCount = timer.getDurationOfTrainingIntervals() - (minutesCount * 60); //number of seconds in duration of training intervals

                    start.set(Calendar.HOUR_OF_DAY, (int) hoursCount);
                    start.set(Calendar.MINUTE, (int) minutesCount);
                    start.set(Calendar.SECOND, (int) secondsCount);
                } else {
                    start.set(Calendar.HOUR_OF_DAY, 0);
                    start.set(Calendar.MINUTE, 0);
                    start.set(Calendar.SECOND, 0);
                }
                MyTimePickerDialog trainingIntervalsTimePicker = new MyTimePickerDialog(getContext(), new MyTimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds) {
                        String durationTraining = String.format("%02d", hourOfDay) +
                                ":" + String.format("%02d", minute) +
                                ":" + String.format("%02d", seconds);

                        txtDialogAddAndEditDurationsTraining.setText(durationTraining);

                    }
                }, start.get(Calendar.HOUR_OF_DAY), start.get(Calendar.MINUTE), start.get(Calendar.SECOND), true);
                trainingIntervalsTimePicker.show();

            }
        });

        txtDialogAddAndEditDurationsBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar start = Calendar.getInstance();

                if (!isAdding) {
                    long hoursCount = timer.getDurationOfTrainingIntervals() / (60 * 60); //number of hours in duration of training intervals
                    long minutesCount = timer.getDurationOfTrainingIntervals() / 60 - (hoursCount * 60 * 60); //number of minutes in duration of training intervals
                    long secondsCount = timer.getDurationOfTrainingIntervals() - (minutesCount * 60); //number of seconds in duration of training intervals

                    start.set(Calendar.HOUR_OF_DAY, (int) hoursCount);
                    start.set(Calendar.MINUTE, (int) minutesCount);
                    start.set(Calendar.SECOND, (int) secondsCount);
                } else {
                    start.set(Calendar.HOUR_OF_DAY, 0);
                    start.set(Calendar.MINUTE, 0);
                    start.set(Calendar.SECOND, 0);
                }
                MyTimePickerDialog trainingIntervalsTimePicker = new MyTimePickerDialog(getContext(), new MyTimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds) {

                        String durationBreak = String.format("%02d", hourOfDay) +
                                ":" + String.format("%02d", minute) +
                                ":" + String.format("%02d", seconds);

                        txtDialogAddAndEditDurationsBreak.setText(durationBreak);
                    }
                }, start.get(Calendar.HOUR_OF_DAY), start.get(Calendar.MINUTE), start.get(Calendar.SECOND), true);
                trainingIntervalsTimePicker.show();
            }
        });

        btnDialogAddAndEditDurationsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtDialogAddAndEditDurationsTraining.getText().toString().equals("") ||
                        !txtDialogAddAndEditDurationsBreak.getText().toString().contains(":"))  //make sure not to be empty or has never set
                {
                    Toast.makeText(getContext(), "Please enter duration of each training interval", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (txtDialogAddAndEditDurationsBreak.getText().toString().equals("")||
                !txtDialogAddAndEditDurationsBreak.getText().toString().contains(":"))  //make sure not to be empty or has never set
                {
                    Toast.makeText(getContext(), "Please enter duration of each break interval", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtDialogAddAndEditNumberOfTrainingIntervals.getText().toString().equals(""))  //make sure not to be empty
                {
                    Toast.makeText(getContext(), "Please enter Number of training intervals", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isAdding) {
                    Timer timer = new Timer();
                    String[] durationTrainingSplits = txtDialogAddAndEditDurationsTraining.getText().toString().split(":");
                    timer.setDurationOfTrainingIntervals((Long.parseLong(durationTrainingSplits[0]) * 60 * 60) +
                            (Long.parseLong(durationTrainingSplits[1]) * 60) + Long.parseLong(durationTrainingSplits[2]));

                    String[] durationBreakSplits = txtDialogAddAndEditDurationsBreak.getText().toString().split(":");
                    timer.setDurationOfBreakIntervals((Long.parseLong(durationBreakSplits[0]) * 60 * 60) +
                            (Long.parseLong(durationBreakSplits[1]) * 60) +
                            Long.parseLong(durationBreakSplits[2]));

                    timer.setNumberOfTrainingIntervals(Long.parseLong(edtDialogAddAndEditNumberOfTrainingIntervals.getText().toString()));

                    viewModelTimer.insert(timer);

                    Paper.book().write("#HasTimer", 0);
                } else {
                    String[] durationTrainingSplits = txtDialogAddAndEditDurationsTraining.getText().toString().split(":");
                    timer.setDurationOfTrainingIntervals((Long.parseLong(durationTrainingSplits[0]) * 60 * 60)
                            + (Long.parseLong(durationTrainingSplits[1]) * 60) +
                            Long.parseLong(durationTrainingSplits[2]));

                    String[] durationBreakSplits = txtDialogAddAndEditDurationsBreak.getText().toString().split(":");
                    timer.setDurationOfBreakIntervals((Long.parseLong(durationBreakSplits[0]) * 60 * 60) +
                            (Long.parseLong(durationBreakSplits[1]) * 60) +
                            Long.parseLong(durationBreakSplits[2]));

                    timer.setNumberOfTrainingIntervals(Long.parseLong(edtDialogAddAndEditNumberOfTrainingIntervals.getText().toString()));

                    viewModelTimer.update(timer);
                }
                dismiss();
            }
        });

        btnDialogAddAndEditDurationsCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }

        //to not show white background behind the surrounded
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        dialog.setCancelable(false);
    }
}

