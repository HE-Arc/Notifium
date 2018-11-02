package devmobile.hearc.ch.notifium;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class ListAlertItemAdapter extends ArrayAdapter<ListAlertItem> {

    private List<ListAlertItem> dataSet;

    public ListAlertItemAdapter(Context context, int resource, List<ListAlertItem> dataSet) {
        super(context, resource);
        this.dataSet = dataSet;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public ListAlertItem getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            // Inflate layout
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter, parent, false);

            // Setup ViewHolder
            holder = new ViewHolder();
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
            holder.textView = (TextView) convertView.findViewById(R.id.text_view);

            // Store ViewHolder with this row view
            convertView.setTag(holder);

        } else {

            // Use viewHolder - quicker than calling findViewById() on resource every time
            holder = (ViewHolder) convertView.getTag();
        }

        // Get item from your data set at the current list position
        ListAlertItem checkItem = getItem(position);

        if (checkItem != null) {
            // Set the views to match the item from your data set
            holder.checkBox.setChecked(checkItem.isChecked());
            holder.textView.setText(checkItem.getName());
        }

        return convertView;
    }

    // ViewHolder acts as a cache for row views
    private class ViewHolder {
        TextView textView;
        CheckBox checkBox;
    }
}
