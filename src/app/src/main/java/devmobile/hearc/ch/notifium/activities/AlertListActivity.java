/**
 * Projet : Notifium
 * Students : Raphaël Margueron / Fabien Mottier / Segan Salomon
 * Teacher : Aicha Rizzotti
 * Module : 3255.1-Developpement_mobile
 * Repository Git : https://github.com/HE-Arc/Notifium
 * Date : 25.01.2019
 */

package devmobile.hearc.ch.notifium.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import devmobile.hearc.ch.notifium.AlertAdapter;
import devmobile.hearc.ch.notifium.R;

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

    public static Context context;
    private Button addAlertButton;

    private TabLayout alertsLayout;
    private ListView alertListView;
    private AlertAdapter alertAdapter;

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckPermissions();

        context = this.getApplicationContext();
        setContentView(R.layout.activity_alert_list);

        retrieveViews();
        setUpViews();
    }

    /**
     * Ask permission to the user
     * @return
     */
    private void CheckPermissions()
    {
        ArrayList<String> missingPermissions  = new ArrayList<String>();

        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }

        if (!missingPermissions.isEmpty())
        {
            requestPermissions(missingPermissions.toArray(new String[missingPermissions.size()]), REQUEST_CODE_ASK_PERMISSIONS);
        }
        else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS, grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index] + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                // all permissions were granted
                initialize();
                break;
        }
    }

    private void initialize()
    {
        // Start service
        updateRows();
        //context.startForegroundService(new Intent(context, NotifierService.class));
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
        alertAdapter = new AlertAdapter();

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
