package devmobile.hearc.ch.notifium;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
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
public class AlertAdapter extends BaseAdapter implements Filterable {

    /**
     * The context (activity) in which this adapter is used.
     */
    public static Context CONTEXT;

    /**
     * The filtered garbages; the garbages currently shown on the list.
     */
    private List<Alert> filteredGarbages;

    /**
     * The filter object, handling the filtering of our garbages.
     */
    private Filter garbageFilter;

    public AlertAdapter(Context context) {
        super();

        this.CONTEXT = context;

        construct();
    }

    private void construct() {
        // Display all garbages by default
        filteredGarbages = GarbageStore.GARBAGES;

        // Create our garbages' filter
        garbageFilter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint == null) {
                    // No constraint -> show all

                    filteredGarbages = GarbageStore.GARBAGES;
                } else {
                    filteredGarbages = new ArrayList<>();

                    for (Alert garbage : GarbageStore.GARBAGES) {
                        // Filter by garbage name

                        if (garbage.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredGarbages.add(garbage);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();

                filterResults.values = filteredGarbages;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // New filtering -> notify the list adapter (BaseAdapter) that its content changed
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return filteredGarbages.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredGarbages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GarbageHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(CONTEXT).inflate(R.layout.garbage_list_row, parent, false);

            holder = new GarbageHolder();

            holder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            holder.categoryNameTextView = (TextView) convertView.findViewById(R.id.categoryNameTextView);

            /*
             * We have 2 views, but we only can set one object (tag) into our convertView object.
             *
             * Now we see the whole purpose of our "XyzHolder" wrapper classes.
             */
            convertView.setTag(holder);
        } else {
            holder = (GarbageHolder) convertView.getTag();
        }

        Garbage garbage = filteredGarbages.get(position);

        holder.nameTextView.setText(garbage.getName());
        holder.categoryNameTextView.setText(
                String.format(
                        CONTEXT.getResources().getString(R.string.garbage_category_list_row),
                        garbage.getCategory().getName()
                )
        );

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return garbageFilter;
    }

    /**
     * Wrapper class for our garbage views.
     */
    private static class GarbageHolder {
        TextView nameTextView;
        TextView categoryNameTextView;
    }
}
