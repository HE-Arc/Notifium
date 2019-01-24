/**
 * Projet : Notifium
 * Students : Raphaël Margueron / Fabien Mottier / Segan Salomon
 * Teacher : Aicha Rizzotti
 * Module : 3255.1-Developpement_mobile
 * Repository Git : https://github.com/HE-Arc/Notifium
 * Date : 25.01.2019
 */
package devmobile.hearc.ch.notifium.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import devmobile.hearc.ch.notifium.AlertStorage;
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

/***
 * This activity is used to create new Alert into the AlertStorage singleton
 */

public class AddAlertActivity extends AppCompatActivity {

    //Declare every controls used in this activity
    private EditText etAlertName;
    private EditText etAlertDescription;

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
    private Button btnDate2;

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

    /***
     * On the creation of the activity
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Date and time format
        formatDate = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        formatTime = DateTimeFormatter.ofPattern("HH:mm");

        //Day of the week checked tracking
        daysOfTheWeek = new boolean[7];
        btnsEveryWeek = new Button[7];

        setContentView(R.layout.activity_add_alert); //load the layout
        loadUI(); //load the ui in the layout into the class
        addEventsListener(); // add every event listener for the activity
        setDefaultValues(); //add defaut values
        updateSave(); //update the state of the save button

    }

    /***
     * Create the menu on top of the screen with an add Alert menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_alert_menu, menu);
        return true;
    }

    /***
     * Close the activity on click of the menu cancel button
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancelNewAlert:
                AddAlertActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Load the layout ui into the class
     */
    private void loadUI() {
        etAlertName = (EditText) findViewById(R.id.etAlertName);
        etAlertDescription = (EditText) findViewById(R.id.etAlertDescription);

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
        etEveryNDays = (EditText) findViewById(R.id.etEveryNDays);
        btnDate2 = (Button) findViewById(R.id.btnDate2);

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


        btnSave = (Button) findViewById(R.id.btnSave);
    }

    /***
     * Check if the alert is valid change set the activation of the button according to it
     */
    private void updateSave() {
        btnSave.setEnabled(isValid());
    }

    /***
     * Check if the alert is valid
     * @return the state of the alert
     */
    private boolean isValid() {
        boolean valid;

        boolean hasName = !etAlertName.getText().toString().equals("");
        boolean hasDesc = !etAlertDescription.getText().toString().equals("");
        boolean atLeastOnChecked = switchDateTime.isChecked() || switchLocation.isChecked() || switchBattery.isChecked();

        valid = hasName && hasDesc && atLeastOnChecked;

        //Periodic
        if (switchDateTime.isChecked() && switchPeriodic.isChecked()) {
            if (rbtnEveryMonth.isChecked()) {
                // if input is enter
                if (!etEveryMonth.getText().toString().isEmpty()) {
                    // if input is valid
                    if (Integer.parseInt(etEveryMonth.getText().toString()) <= 0) {
                        valid = false;
                    }
                } else {
                    valid = false;
                }
            }
            else if (rbtnEveryNDays.isChecked()) {
                // if input is enter
                if (!etEveryNDays.getText().toString().isEmpty()) {
                    // if input is valid
                    if (Integer.parseInt(etEveryNDays.getText().toString()) <= 0) {
                        valid = false;
                    }
                } else {
                    valid = false;
                }
            }
            else if (rbtnEveryWeek.isChecked()) {
                boolean atLeastOneDay = false;
                for (int i = 0; i < btnsEveryWeek.length; ++i) {
                    if (btnsEveryWeek[i].isSelected())
                        atLeastOneDay = true;
                }
                if (!atLeastOneDay) {
                    valid = false;
                }
            } else {
                valid = false;
            }
        }


        return valid;
    }

    /***
     * Add every events for the activity
     */
    private void addEventsListener() {


        //Text edits
        etAlertName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSave();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etAlertDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSave();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Switch to activate submenus
        switchDateTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutDateTime.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                updateSave();
            }
        });

        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutLocation.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                updateSave();
            }
        });

        switchBattery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutBattery.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                updateSave();
            }
        });

        switchPeriodic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutPeriodic.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                layoutDate.setVisibility(!isChecked ? View.VISIBLE : View.GONE);
                updateSave();
            }
        });

        //The set the date
        View.OnClickListener vcl = new View.OnClickListener() {
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
        };

        //To set the date
        btnDate.setOnClickListener(vcl); //button 1
        btnDate2.setOnClickListener(vcl); //button 2


        //Set the time
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

        //Radio button to set the alert to every n days
        rbtnEveryNDays.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutEveryNDays.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                updateSave();
            }
        });

        // Add a filter to the text edit for the every month textedit
        etEveryMonth.setFilters(new InputFilter[]{new MinMaxFilter("1", "31")});


        //Radio button to set the alert to every week
        rbtnEveryWeek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutEveryWeek.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                updateSave();
            }
        });

        //Radio button to set the alert to every month
        rbtnEveryMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layoutEveryMonth.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                updateSave();
            }
        });


        //Handle the label according to the value of the bar for the batterie
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

        //On click of the save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert alert = createAlert();
                AlertStorage.getInstance().addAlert(alert);
                AlertStorage.getInstance().save(getApplicationContext());
                AddAlertActivity.this.finish();
            }
        });

        //Add for every week buttons the toggle
        for (int i = 0; i < 7; i++) {
            final int finalI = i;
            btnsEveryWeek[finalI].setBackgroundColor(0xFF3F51B5);
            btnsEveryWeek[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    daysOfTheWeek[finalI] = !daysOfTheWeek[finalI];
                    if (daysOfTheWeek[finalI])
                        btnsEveryWeek[finalI].setBackgroundColor(0xFFFA3F7E);
                    else
                        btnsEveryWeek[finalI].setBackgroundColor(0xFF3F51B5);
                }
            });
            daysOfTheWeek[i] = false;
        }
    }

    /***
     * Add default values to now
     */
    private void setDefaultValues() {
        date = LocalDate.now();
        time = LocalTime.now();
        updateDate();
        updateTime();
    }

    /***
     * Update the dates buttons
     */
    private void updateDate() {

        btnDate.setText(date.format(formatDate));
        btnDate2.setText(date.format(formatDate));
    }

    /***
     * Update the time buttons
     */
    private void updateTime() {
        btnTime.setText(time.format(formatTime));
    }

    /***
     * Create an alerte according to the ui
     * @return
     */
    private Alert createAlert() {
        Alert alert = new Alert(etAlertName.getText().toString(), etAlertDescription.getText().toString());

        if (switchDateTime.isEnabled()) {
            Trigger trigger = new Trigger();
            trigger.add(new ConditionHour(time));

            if (!switchPeriodic.isEnabled()) {
                trigger.add(new ConditionDate(date));
            } else {
                if (rbtnEveryNDays.isChecked()) {
                    int dt = Integer.parseInt(etEveryNDays.getText().toString());
                    ConditionDateEveryNDay cond = new ConditionDateEveryNDay(date, dt);
                    trigger.add(cond);
                } else if (rbtnEveryWeek.isChecked()) {
                    for (int i = 0; i < daysOfTheWeek.length; i++) {
                        if (daysOfTheWeek[i]) {
                            ConditionDateDayOfWeek cond = new ConditionDateDayOfWeek(DayOfWeek.of(i));
                            trigger.add(cond);
                        }
                    }
                } else if (rbtnEveryMonth.isChecked()) {
                    int val = Integer.parseInt(etEveryMonth.getText().toString());
                    ConditionDateMonthly cond = new ConditionDateMonthly(val);
                    trigger.add(cond);
                }
            }

            alert.add(trigger);
        }

        if (switchLocation.isEnabled()) {
            Trigger trigger = new Trigger();
            trigger.add(new ConditionLocalisation(0, 0, 0));
            alert.add(trigger);
        }

        if (switchBattery.isEnabled()) {
            Trigger trigger = new Trigger();
            trigger.add(new ConditionBatteryLevel(seekBarBattery.getProgress()));
            alert.add(trigger);
        }


        return alert;
    }
}
