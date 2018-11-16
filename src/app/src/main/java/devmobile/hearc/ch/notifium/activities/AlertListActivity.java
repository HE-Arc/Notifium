package devmobile.hearc.ch.notifium.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.Observable;
import java.util.Observer;

import devmobile.hearc.ch.notifium.R;
import devmobile.hearc.ch.notifium.AlertAdapter;

/**
 * Show the garbage list.
 *
 * Within this activity, we can:
 *
 *   - see all garbages in our application;
 *   - filter the garbages with a search view;
 *   - start an intent to create a garbage;
 *   - start an intent to see a garbage details.
 */
public class AlertListActivity extends ObserverActivity {

    private Button addAlertButton;

    private TabLayout alertsLayout;
    private ListView alertListView;
    private AlertAdapter alertAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alert_list);

        retrieveViews();
        setUpViews();
        updateRows();
    }

    /**
     * Retrieve all views inside res/layout/garbage_list_activity.xml.
     */
    private void retrieveViews() {
        addAlertButton = (Button) findViewById(R.id.addAlertButton);
        alertListView = (ListView) findViewById(R.id.alertListView);
        alertsLayout = (TabLayout) findViewById(R.id.tabLayout);
    }

    @Override
    public void update(Observable o, Object arg) {
        updateRows();
    }

    private void updateRows()
    {
        alertListView.post(new Runnable() {
            @Override
            public void run() {
                alertAdapter.notifyDataSetChanged();
                alertListView.smoothScrollToPosition(0);
            }
        });
    }

    /**
     * Construct our logic. What we wants is the following:
     *
     *   - being able to filter the garbage list;
     *   - being able to see a garbage details by clicking an item in the list;
     *   - being able to start the creation of a new garbage by clicking the "Add" button.
     */
    private void setUpViews() {
        alertAdapter = new AlertAdapter(this);

        // Tell by which adapter we will handle our list
        alertListView.setAdapter(alertAdapter);

        // See a garbage details when clicking on it
        /*garbageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Garbage garbage = (Garbage) garbageListView.getItemAtPosition(position);

                Intent intent = new Intent(AlertListActivity.this, GarbageDetailsActivity.class);

                intent.putExtra("garbageName", garbage.getName());

                startActivity(intent);
            }
        });*/

        // Start the activity to add a garbage when clicking the "Add" button
        addAlertButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertListActivity.this, AddAlertActivity.class);
                startActivity(intent);
            }
        });

        alertsLayout.setOnTabSelectedListener(
            new TabLayout.BaseOnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition())
                    {
                        case 0:
                            alertAdapter.displayAll();
                            break;
                        case 1:
                            alertAdapter.displayDates();
                            break;
                        case 2:
                            alertAdapter.displayPositions();
                            break;
                        case 3:
                            alertAdapter.displayBattery();
                            break;
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            }
        );
    }

    @Override
    protected void onResume() {
        // Important! Refresh our list when we return to this activity (from another one)
        alertAdapter.notifyDataSetChanged();

        super.onResume();
    }
}
