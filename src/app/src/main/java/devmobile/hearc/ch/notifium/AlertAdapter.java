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
import devmobile.hearc.ch.notifium.logicals.Alert;

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
    private List<Alert> filteredAlerts = new ArrayList<Alert>();

    public AlertAdapter(Context context) {
        super();
        this.CONTEXT = context;
    }

    @Override
    public int getCount() {
        return filteredAlerts.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredAlerts.get(position);
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

        Alert alert = filteredAlerts.get(position);

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
    }
}