package devmobile.hearc.ch.notifium;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class ListAlerts extends AppCompatActivity {

    private Button btnAddAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_alerts);

        loadUI();
    }

    private void loadUI()
    {
        btnAddAlert = (Button) findViewById(R.id.addAlert);
    }
}
