package devmobile.hearc.ch.notifium.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class AddAlert extends AppCompatActivity {

    private Switch switchTime;
    private LinearLayout layoutTime;

    private Switch switchLocation;
    private LinearLayout layoutLocation;

    private Switch switchBattery;
    private LinearLayout layoutBattery;
    private SeekBar seekBarBattery;
    private TextView textViewBattery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alert);
        loadUI();
        addEventsListener();
    }

    private void loadUI()
    {
        switchTime = (Switch) findViewById(R.id.switchTime);
        layoutTime = (LinearLayout) findViewById(R.id.layoutTime);

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
