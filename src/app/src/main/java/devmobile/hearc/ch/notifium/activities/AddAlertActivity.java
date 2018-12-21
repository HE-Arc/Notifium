package devmobile.hearc.ch.notifium.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import devmobile.hearc.ch.notifium.R;
import devmobile.hearc.ch.notifium.logicals.Alert;
import devmobile.hearc.ch.notifium.logicals.Trigger;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionBatteryLevel;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDate;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDateDayOfWeek;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDateEveryNDay;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDateMonthly;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionHour;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionLocalisation;
import devmobile.hearc.ch.notifium.tools.MinMaxFilter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AddAlertActivity extends AppCompatActivity {

    private EditText etAlertName;

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
    private EditText etEveryNDays;

    private RadioButton rbtnEveryWeek;
    private LinearLayout layoutEveryWeek;
    private Button[] btnsEveryWeek;

    private RadioButton rbtnEveryMonth;
    private LinearLayout layoutEveryMonth;
    private EditText etEveryMonth;

    private Switch switchLocation;
    private LinearLayout layoutLocation;

    private Switch switchBattery;
    private LinearLayout layoutBattery;
    private SeekBar seekBarBattery;
    private TextView textViewBattery;

    private Button btnSave;

    private DateTimeFormatter formatDate;
    private DateTimeFormatter formatTime;

    private LocalTime time;
    private LocalDate date;

    private boolean[] daysOfTheWeek;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        formatDate = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        formatTime = DateTimeFormatter.ofPattern("HH:mm");
        daysOfTheWeek = new boolean[7];
        btnsEveryWeek = new Button[7];

        setContentView(R.layout.activity_add_alert);
        loadUI();
        addEventsListener();
        setDefaultValues();
        updateSave();

    }

    private void loadUI()
    {
        etAlertName = (EditText) findViewById(R.id.etAlertName);

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
        etEveryNDays = (EditText)findViewById(R.id.etEveryNDays);

        layoutEveryWeek = (LinearLayout) findViewById(R.id.layoutEveryWeek);
        rbtnEveryWeek = (RadioButton) findViewById(R.id.rbtnEveryWeek);
        btnsEveryWeek[0] = (Button) findViewById(R.id.everyWeek0);
        btnsEveryWeek[1] = (Button) findViewById(R.id.everyWeek1);
        btnsEveryWeek[2] = (Button) findViewById(R.id.everyWeek2);
        btnsEveryWeek[3] = (Button) findViewById(R.id.everyWeek3);
        btnsEveryWeek[4] = (Button) findViewById(R.id.everyWeek4);
        btnsEveryWeek[5] = (Button) findViewById(R.id.everyWeek5);
        btnsEveryWeek[6] = (Button) findViewById(R.id.everyWeek6);

        layoutEveryMonth = (LinearLayout) findViewById(R.id.layoutEveryMonth);
        rbtnEveryMonth = (RadioButton) findViewById(R.id.rbtnEveryMonth);
        etEveryMonth = (EditText) findViewById(R.id.etEveryMonth);


        // TODO: Voir Arcgis ou Mapbox
        switchLocation = (Switch) findViewById(R.id.switchLocation);
        layoutLocation = (LinearLayout) findViewById(R.id.layoutLocation);

        switchBattery = (Switch) findViewById(R.id.switchBattery);
        layoutBattery = (LinearLayout) findViewById(R.id.layoutBattery);

        seekBarBattery = (SeekBar) findViewById(R.id.seekBarBattery);
        textViewBattery = (TextView) findViewById(R.id.textViewBattery);

        btnSave = (Button) findViewById(R.id.btnSave);
    }

    private void updateSave()
    {
        btnSave.setEnabled(isValid());
    }

    private boolean isValid()
    {
        boolean valid;

        boolean hasName = !etAlertName.getText().toString().equals("");
        boolean atLeastOnChecked = switchDateTime.isChecked() || switchLocation.isChecked() || switchBattery.isChecked();

        valid = hasName && atLeastOnChecked;

        //Periodic
        if(switchDateTime.isChecked() && switchPeriodic.isChecked())
        {
            boolean atLeastOneRadio = rbtnEveryMonth.isChecked() || rbtnEveryNDays.isChecked() || rbtnEveryNDays.isChecked();
            if(!atLeastOneRadio)
                valid = false;
        }


        return valid;
    }

    private void addEventsListener() {

        etAlertName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                updateSave();
                return false;
            }
        });

        switchDateTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutDateTime.setVisibility(isChecked ? View.VISIBLE: View.GONE);
                updateSave();
            }
        });

        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutLocation.setVisibility(isChecked ? View.VISIBLE: View.GONE);
                updateSave();
            }
        });

        switchBattery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutBattery.setVisibility(isChecked ? View.VISIBLE: View.GONE);
                updateSave();
            }
        });

        switchPeriodic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutPeriodic.setVisibility(isChecked ? View.VISIBLE: View.GONE);
                layoutDate.setVisibility(!isChecked ? View.VISIBLE: View.GONE);
                updateSave();
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
                        date = LocalDate.of(year, month, dayOfMonth);
                        updateDate();
                    }
                }, year, month, dayOfMonth);
                dpd.show();
                updateSave();
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
                        time = LocalTime.of(selectedHour, selectedMinute);
                        updateTime();
                    }
                }, hour, minute, true);
                mTimePicker.show();
                updateSave();
            }
        });

        rbtnEveryNDays.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutEveryNDays.setVisibility(isChecked ? View.VISIBLE: View.GONE);
                updateSave();
            }
        });

        etEveryMonth.setFilters(new InputFilter[]{ new MinMaxFilter("1", "31")});

        rbtnEveryWeek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutEveryWeek.setVisibility(isChecked ? View.VISIBLE: View.GONE);
                updateSave();
            }
        });

        rbtnEveryMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutEveryMonth.setVisibility(isChecked ? View.VISIBLE: View.GONE);
                updateSave();
            }
        });

        seekBarBattery.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewBattery.setText("" + progress + "%");
                updateSave();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert alert = createAlert();
            }
        });

        for(int i = 0; i < 7; i++)
        {
            final int finalI = i;
            btnsEveryWeek[finalI].setBackgroundColor(0xFF3F51B5);
            btnsEveryWeek[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    daysOfTheWeek[finalI] = !daysOfTheWeek[finalI];
                    if(daysOfTheWeek[finalI])
                        btnsEveryWeek[finalI].setBackgroundColor(0xFFFA3F7E);
                    else
                        btnsEveryWeek[finalI].setBackgroundColor(0xFF3F51B5);
                }
            });
            daysOfTheWeek[i] = false;
        }
    }

    private void setDefaultValues()
    {
        date = LocalDate.now();
        time = LocalTime.now();
        updateDate();
        updateTime();
    }

    private void updateDate()
    {
        btnDate.setText(date.format(formatDate));
    }

    private void updateTime()
    {
        btnTime.setText(time.format(formatTime));
    }

    private Alert createAlert()
    {
        Alert alert = new Alert(etAlertName.getText().toString());

        if(switchDateTime.isEnabled())
        {
            Trigger trigger = new Trigger();
            trigger.add(new ConditionHour(time));

            if(!switchPeriodic.isEnabled())
            {
                trigger.add(new ConditionDate(date));
            }
            else
            {
                if(rbtnEveryNDays.isChecked())
                {
                    int dt = Integer.parseInt(etEveryNDays.getText().toString());
                    ConditionDateEveryNDay cond = new ConditionDateEveryNDay(dt);
                    trigger.add(cond);
                }
                else if(rbtnEveryWeek.isChecked())
                {
                    for(int i = 0; i < daysOfTheWeek.length; i++)
                    {
                        if(daysOfTheWeek[i])
                        {
                            ConditionDateDayOfWeek cond = new ConditionDateDayOfWeek(DayOfWeek.of(i));
                            trigger.add(cond);
                        }
                    }
                }
                else if(rbtnEveryMonth.isChecked())
                {
                    int val = Integer.parseInt(etEveryMonth.getText().toString());
                    ConditionDateMonthly cond = new ConditionDateMonthly(val);
                    trigger.add(cond);
                }
            }

            alert.add(trigger);
        }

        if(switchLocation.isEnabled())
        {
            Trigger trigger = new Trigger();
            trigger.add(new ConditionLocalisation(0,0,0));
            alert.add(trigger);
        }

        if(switchBattery.isEnabled())
        {
            Trigger trigger = new Trigger();
            trigger.add(new ConditionBatteryLevel(seekBarBattery.getProgress()));
            alert.add(trigger);
        }


        return alert;
    }
}
