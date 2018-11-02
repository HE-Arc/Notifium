package devmobile.hearc.ch.notifium;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class AddAlert extends AppCompatActivity {

    private DatePickerDialog dpd;
    private TimePickerDialog tpd;

    private Switch switchTime;
    private LinearLayout layoutTime;

    private Button btnTime;
    private Button btnDate;

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
        dpd = new DatePickerDialog(this);
        //tpd = new TimePickerDialog(this);
        setContentView(R.layout.activity_add_alert);
        loadUI();
        addEventsListener();
    }

    private void loadUI()
    {
        switchTime = (Switch) findViewById(R.id.switchTime);
        layoutTime = (LinearLayout) findViewById(R.id.layoutTime);

        btnTime = (Button) findViewById(R.id.btnTime);
        btnDate = (Button) findViewById(R.id.btnDate);

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

        switchTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutTime.setVisibility(isChecked ? View.VISIBLE: View.GONE);
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
                btnDate.setVisibility(!isChecked ? View.VISIBLE: View.GONE);
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
}
