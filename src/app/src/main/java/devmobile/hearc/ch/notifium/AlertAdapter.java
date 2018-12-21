package devmobile.hearc.ch.notifium;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.time.DayOfWeek;

import devmobile.hearc.ch.notifium.activities.ObserverActivity;
import devmobile.hearc.ch.notifium.filters.Filters;
import devmobile.hearc.ch.notifium.logicals.Alert;
import devmobile.hearc.ch.notifium.logicals.Trigger;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionBatteryLevel;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDateDayOfWeek;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionHour;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionLocalisation;
import devmobile.hearc.ch.notifium.logicals.conditions.Condition_I;

/**
 * Our garbage adapter for list views containing garbages.
 *
 * We want to display the following text per row:
 *
 *   - GARBAGE_NAME (GARBAGE_CATEGORY_NAME)
 */
public class AlertAdapter extends BaseAdapter {

    public AlertAdapter(ObserverActivity context) {
        super();

        // Hour alerts
        for(int i = 0; i < 10; i++) {
            // Create an alert
            Alert a = new Alert("MyAlert" + i);
            Trigger t = new Trigger();
            Condition_I ch = new ConditionHour(i + 10, 30);
            t.add(ch);
            a.add(t);

            // store it in list
            AlertStorage.getInstance().addAlert(a);
        }

        // Battery alerts
        for(int i = 0; i < 10; i++) {
            // Create an alert
            Alert a = new Alert("MyAlert" + i);
            Trigger t = new Trigger();
            Condition_I c = new ConditionBatteryLevel(i * 10);
            t.add(c);
            a.add(t);

            // store it in list
            AlertStorage.getInstance().addAlert(a);
        }

        // Day alerts
        for(int i = 0; i < 10; i++) {
            // Create an alert
            Alert a = new Alert("MyAlert" + i);
            Trigger t = new Trigger();
            Condition_I c = new ConditionDateDayOfWeek(DayOfWeek.of((i % 7) + 1));
            t.add(c);
            a.add(t);

            // store it in list
            AlertStorage.getInstance().addAlert(a);
        }

        // Location alerts
        for(int i = 0; i < 10; i++) {
            // Create an alert
            Alert a = new Alert("MyAlert" + i);
            Trigger t = new Trigger();
            Condition_I c = new ConditionLocalisation(1, 1, 10);
            t.add(c);
            a.add(t);

            // store it in list
            AlertStorage.getInstance().addAlert(a);
        }

        // Get filtered alerts
        AlertStorage.getInstance().applyFilter(Filters.ALL);
        AlertStorage.getInstance().save(context);
        AlertStorage.getInstance().load(context);
    }

    @Override
    public int getCount() {
        return AlertStorage.getInstance().getFilteredAlerts().size();
    }

    public void displayAll()
    {
        AlertStorage.getInstance().applyFilter(Filters.ALL);
        notifyDataSetChanged();
    }

    public void displayDates()
    {
        AlertStorage.getInstance().applyFilter(Filters.TIME);
        notifyDataSetChanged();
    }

    public void displayPositions()
    {
        AlertStorage.getInstance().applyFilter(Filters.POSITION);
        notifyDataSetChanged();
    }

    public void displayBattery()
    {
        AlertStorage.getInstance().applyFilter(Filters.BATTERY);
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return AlertStorage.getInstance().getFilteredAlerts().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlertHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(convertView.getContext()).inflate(R.layout.row_alert_list, parent, false);

            holder = new AlertHolder();

            holder.selectCheckBox = convertView.findViewById(R.id.selectCheckBox);
            holder.nameTextView = convertView.findViewById(R.id.nameTextView);
            holder.editButton = convertView.findViewById(R.id.editButton);
            holder.suppressButton = convertView.findViewById(R.id.suppressButton);

            convertView.setTag(holder);
        } else {
            holder = (AlertHolder) convertView.getTag();
        }

        // Get alert
        final Alert alert = AlertStorage.getInstance().getFilteredAlert(position);

        // Set data
        holder.selectCheckBox.setChecked(false);
        holder.nameTextView.setText(alert.getName());
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO
            }
        });
        holder.suppressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertStorage.getInstance().removeAlert(alert);
            }
        });

        return convertView;
    }

    /**
     * Wrapper class for our garbage views.
     */
    private static class AlertHolder {
        CheckBox selectCheckBox;
        TextView nameTextView;
        Button editButton;
        Button suppressButton;
    }
}