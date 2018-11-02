package devmobile.hearc.ch.notifium;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListAlerts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_alerts);

        List<ListAlertItem> checkItems = new ArrayList<>();

        // Provide adapter with dummy data for testing
        for (int i = 0; i < 50; i++) {
            checkItems.add(new ListAlertItem("Check Item " + (i + 1), i % 2 == 0));
        }

        ListAlertItemAdapter adapter = new ListAlertItemAdapter(this, R.layout.adapter, checkItems);
        ListView listView = (ListView) findViewById(R.id.listView_alert_items);

        listView.setAdapter(adapter);
    }
}
