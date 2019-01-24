/**
 * Projet : Notifium
 * Students : Raphaël Margueron / Fabien Mottier / Segan Salomon
 * Teacher : Aicha Rizzotti
 * Module : 3255.1-Developpement_mobile
 * Repository Git : https://github.com/HE-Arc/Notifium
 * Date : 25.01.2019
 */
package devmobile.hearc.ch.notifium;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import devmobile.hearc.ch.notifium.filters.Filters;
import devmobile.hearc.ch.notifium.logicals.Alert;

/**
 * Used to control what is shown to the user with the list
 */
public class AlertAdapter extends BaseAdapter {

    public AlertAdapter(Context context) {
        super();

        AlertStorage.load(context);
        // Serve as a demo
        AlertStorage.getInstance().seed();
    }

    @Override
    public int getCount() {
        return AlertStorage.getInstance().getFilteredAlerts().size();
    }

    /**
     * Apply the filter ALL to the alertStorage
     * Notify that the dataset has changed in order to refresh UI
     */
    public void displayAll()
    {
        AlertStorage.getInstance().applyFilter(Filters.ALL);
        notifyDataSetChanged();
    }

    /**
     * Apply the filter TIME to the alertStorage
     * Notify that the dataset has changed in order to refresh UI
     */
    public void displayDates()
    {
        AlertStorage.getInstance().applyFilter(Filters.TIME);
        notifyDataSetChanged();
    }

    /**
     * Apply the filter POSITION to the alertStorage
     * Notify that the dataset has changed in order to refresh UI
     */
    public void displayPositions()
    {
        AlertStorage.getInstance().applyFilter(Filters.POSITION);
        notifyDataSetChanged();
    }

    /**
     * Apply the filter BATTERY to the alertStorage
     * Notify that the dataset has changed in order to refresh UI
     */
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

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_alert_list, parent, false);

            holder = new AlertHolder();

            holder.nameTextView = convertView.findViewById(R.id.nameTextView);
            holder.suppressButton = convertView.findViewById(R.id.suppressButton);

            convertView.setTag(holder);
        } else {
            holder = (AlertHolder) convertView.getTag();
        }

        // Get alert
        final Alert alert = AlertStorage.getInstance().getFilteredAlert(position);

        // Set data
        holder.nameTextView.setText(alert.getName());
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
        //Button editButton;
        Button suppressButton;
    }
}