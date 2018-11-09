package devmobile.hearc.ch.notifium;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import devmobile.hearc.ch.notifium.R;
import devmobile.hearc.ch.notifium.activities.ObserverActivity;
import devmobile.hearc.ch.notifium.logicals.Alert;
import devmobile.hearc.ch.notifium.logicals.Trigger;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionHour;

/**
 * Our garbage adapter for list views containing garbages.
 *
 * We want to display the following text per row:
 *
 *   - GARBAGE_NAME (GARBAGE_CATEGORY_NAME)
 */
public class AlertAdapter extends BaseAdapter {

    /**
     * The context (activity) in which this adapter is used.
     */
    public static Context CONTEXT;

    /**
     * The filtered alerts; the alerts currently shown on the list.
     */
    private AlertStorage alerts;

    public AlertAdapter(ObserverActivity context) {
        super();
        this.CONTEXT = context;
        this.alerts = new AlertStorage(context);


        for(int i = 0; i < 10; i++) {
            // Create an alert
            Alert a = new Alert("MyAlert" + i);
            Trigger t = new Trigger();
            ConditionHour ch = new ConditionHour(i + 10, 30);
            t.add(ch);
            a.add(t);

            // store it in list
            alerts.addAlert(a);
        }
    }

    @Override
    public int getCount() {
        return alerts.getAlerts().size();
    }

    @Override
    public Object getItem(int position) {
        return alerts.getAlert(position);
    }

    public Object getItem(String name) {
        return alerts.getAlert(name);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlertHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(CONTEXT).inflate(R.layout.row_alert_list, parent, false);

            holder = new AlertHolder();

            holder.selectCheckBox = convertView.findViewById(R.id.selectCheckBox);
            holder.nameTextView = convertView.findViewById(R.id.nameTextView);
            holder.editButton = convertView.findViewById(R.id.editButton);

            convertView.setTag(holder);
        } else {
            holder = (AlertHolder) convertView.getTag();
        }

        Alert alert = alerts.getAlert(position);

        holder.nameTextView.setText(alert.getName());
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO
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