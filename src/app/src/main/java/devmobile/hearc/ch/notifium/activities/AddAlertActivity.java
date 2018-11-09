package devmobile.hearc.ch.notifium.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import devmobile.hearc.ch.notifium.R;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AddAlertActivity extends AppCompatActivity {

    private Switch switchDateTime;
    private LinearLayout layoutDateTime;

    private Button btnTime;
    private LinearLayout layoutTime;

    private Button btnDate;
    private LinearLayout layoutDate;

    private Switch switchPeriodic;
    private LinearLayout layoutPeriodic;

    private RadioButton rbtnEveryNDays;
    private LinearLayout layoutEveryNDays;
    private Spinner spinnerEveryNDaysWeekDays;

    private RadioButton rbtnEveryWeek;
    private LinearLayout layoutEveryWeek;

    private RadioButton rbtnEveryMonth;
    private LinearLayout layoutEveryMonth;

    private Switch switchLocation;
    private LinearLayout layoutLocation;

    private Switch switchBattery;
    private LinearLayout layoutBattery;
    private SeekBar seekBarBattery;
    private TextView textViewBattery;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alert);
        loadUI();
        addEventsListener();
        setDefaultValues();
    }

    private void loadUI()
    {
        switchDateTime = (Switch) findViewById(R.id.switchDateTime);
        layoutDateTime = (LinearLayout) findViewById(R.id.layoutDateTime);

        btnTime = (Button) findViewById(R.id.btnTime);
        layoutTime = (LinearLayout) findViewById(R.id.layoutTime);

        btnDate = (Button) findViewById(R.id.btnDate);
        layoutDate = (LinearLayout) findViewById(R.id.layoutDate);

        switchPeriodic = (Switch) findViewById(R.id.switchPeriodic);
        layoutPeriodic = (LinearLayout) findViewById(R.id.layoutPeriodic);

        layoutEveryNDays = (LinearLayout) findViewById(R.id.layoutEveryNDays);
        rbtnEveryNDays = (RadioButton) findViewById(R.id.rbtnEveryNDays);
        spinnerEveryNDaysWeekDays = (Spinner) findViewById(R.id.spinnerEveryNDaysWeekDays);

        layoutEveryWeek = (LinearLayout) findViewById(R.id.layoutEveryWeek);
        rbtnEveryWeek = (RadioButton) findViewById(R.id.rbtnEveryWeek);

        layoutEveryMonth = (LinearLayout) findViewById(R.id.layoutEveryMonth);
        rbtnEveryMonth = (RadioButton) findViewById(R.id.rbtnEveryMonth);

        switchLocation = (Switch) findViewById(R.id.switchLocation);
        layoutLocation = (LinearLayout) findViewById(R.id.layoutLocation);

        switchBattery = (Switch) findViewById(R.id.switchBattery);
        layoutBattery = (LinearLayout) findViewById(R.id.layoutBattery);

        seekBarBattery = (SeekBar) findViewById(R.id.seekBarBattery);
        textViewBattery = (TextView) findViewById(R.id.textViewBattery);
    }

    private void addEventsListener() {

        switchDateTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutDateTime.setVisibility(isChecked ? View.VISIBLE: View.GONE);
            }
        });

        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutLocation.setVisibility(isChecked ? View.VISIBLE: View.GONE);
            }
        });

        switchBattery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutBattery.setVisibility(isChecked ? View.VISIBLE: View.GONE);
            }
        });

        switchPeriodic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutPeriodic.setVisibility(isChecked ? View.VISIBLE: View.GONE);
                layoutDate.setVisibility(!isChecked ? View.VISIBLE: View.GONE);
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(AddAlertActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        LocalDate ld = LocalDate.of(year, month, dayOfMonth);
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
                        btnDate.setText(ld.format(dateTimeFormatter));
                    }
                }, year, month, dayOfMonth);
                dpd.show();
            }
        });


        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAlertActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        LocalTime lt = LocalTime.of(selectedHour, selectedMinute);
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;
                        btnTime.setText(lt.format(dateTimeFormatter));
                    }
                }, hour, minute, true);
                mTimePicker.show();
            }
        });

        rbtnEveryNDays.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutEveryNDays.setVisibility(isChecked ? View.VISIBLE: View.GONE);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.addAlertWeekDays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEveryNDaysWeekDays.setAdapter(adapter);

        rbtnEveryWeek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutEveryWeek.setVisibility(isChecked ? View.VISIBLE: View.GONE);
            }
        });

        rbtnEveryMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutEveryMonth.setVisibility(isChecked ? View.VISIBLE: View.GONE);
            }
        });

        seekBarBattery.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewBattery.setText("" + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setDefaultValues()
    {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        btnDate.setText(LocalDate.of(year, month, dayOfMonth).format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        btnTime.setText(LocalTime.of(hour, minute).format(DateTimeFormatter.ISO_LOCAL_TIME));
    }
}
