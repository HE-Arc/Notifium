package devmobile.hearc.ch.notifium;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import devmobile.hearc.ch.notifium.activities.ObserverActivity;
import devmobile.hearc.ch.notifium.logicals.Alert;
import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;
import devmobile.hearc.ch.notifium.logicals.serializer.AlertDeserializer;
import devmobile.hearc.ch.notifium.logicals.serializer.AlertSerializer;

public class AlertStorage extends Observable {

    private final Set<ConditionType> all = new HashSet<ConditionType>();

    /**
     * Filter : all alerts
     */
    public static final Set<ConditionType> ALL = Collections.unmodifiableSet(
            new HashSet<ConditionType>(Arrays.asList(
                    ConditionType.Position,
                    ConditionType.Date,
                    ConditionType.Day,
                    ConditionType.Hour,
                    ConditionType.Battery
                )
            )
    );

    /**
     * Filter : only positioned alerts
     */
    public static final Set<ConditionType> POSITION = Collections.unmodifiableSet(
            new HashSet<ConditionType>(Arrays.asList(
                    ConditionType.Position
                )
            )
    );

    /**
     * Filter : only timed alerts [Date, Day, Hour]
     */
    public static final Set<ConditionType> TIME = Collections.unmodifiableSet(
            new HashSet<ConditionType>(Arrays.asList(
                    ConditionType.Date,
                    ConditionType.Day,
                    ConditionType.Hour
                )
            )
    );

    /**
     * Filter : only battery alerts
     */
    public static final Set<ConditionType> BATTERY = Collections.unmodifiableSet(
            new HashSet<ConditionType>(Arrays.asList(
                    ConditionType.Battery
                )
            )
    );

    // Vars
    private final List<Alert> listAlerts;
    private final List<Alert> filteredAlerts;
    private Set<ConditionType> filter;

    /**
     * Return the list of alerts
     * @return the list of alerts
     */
    public synchronized List<Alert> getFilteredAlerts()
    {
        return filteredAlerts;
    }

    /**
     * Constructor of AlertStorage, by default the filter will be ALL
     * @param observer
     */
    public AlertStorage(ObserverActivity observer)
    {
        listAlerts = new ArrayList<Alert>();
        filteredAlerts = new ArrayList<Alert>();

        this.addObserver(observer);
        this.applyFilter(ALL);
    }

    /**
     * Apply a filter on alerts
     * @param conditionTypes A set of ConditionType
     */
    public void applyFilter(Set<ConditionType> conditionTypes)
    {
        filter = conditionTypes;

        // Flush data
        filteredAlerts.clear();
        for(Alert a : listAlerts)
        {
            // Verify that alert a respect the filter
            boolean respectFilter = false;
            for(ConditionType ct : filter)
            {
                if(a.getConditionsTypes().contains(ct))
                {
                    respectFilter = true;
                    break;
                }
            }

            // Append alert if it respect the filter
            if(respectFilter)
                filteredAlerts.add(a);
        }
    }

    /**
     * Add an alert to the list
     * @param alert An initialized alert
     */
    public synchronized void addAlert(Alert alert)
    {
        listAlerts.add(alert);

        // Filter alerts
        applyFilter(filter);

        this.notifyAll();
    }

    /**
     * Remove an alert to the list
     * @param alert An initialized alert
     */
    public synchronized void removeAlert(Alert alert)
    {
        listAlerts.remove(alert);

        // Filter alerts
        applyFilter(filter);

        this.notifyAll();
    }

    /**
     * Return an alert if found one at index
     * @param index index where to look at
     * @return An alert from the list
     */
    public synchronized Alert getAlert(int index)
    {
        return listAlerts.get(index);
    }

    /**
     * Return an alert if found one with given name
     * @param name name to look for
     * @return An alert from the list
     */
    public synchronized Alert getAlert(String name) {
        for(Alert a : listAlerts)
            if(a.getName() == name) return a;
        return null;
    }

    /**
     * Return an alert if found one at index, search inside the filteredAlerts
     * @param index index where to look at
     * @return An alert from the filtered list
     */
    public synchronized Alert getFilteredAlert(int index)
    {
        return filteredAlerts.get(index);
    }

    /**
     * Return an alert if found one with given name, search inside the filteredAlerts
     * @param name name to look for
     * @return An alert from the filtered list
     */
    public synchronized Alert getFilteredAlert(String name) {
        for(Alert a : filteredAlerts)
            if(a.getName() == name) return a;
        return null;
    }

    /**
     * Return a list containing enabled alerts that respect the filter
     * @return Return a list of alerts
     */
    public synchronized List<Alert> getFilteredEnabledAlerts() {
        List<Alert> output = new ArrayList<>();
        for (Alert a : filteredAlerts) {
            if (a.isEnabled())
                output.add(a);
        }
        return output;
    }

    /**
     * Return a list containing disabled alerts that respect the filter
     * @return Return a list of alerts
     */
    public synchronized List<Alert> getFilteredDisabledAlerts() {
        List<Alert> output = new ArrayList<>();
        for (Alert a : filteredAlerts) {
            if (!a.isEnabled())
                output.add(a);
        }
        return output;
    }

    /**
     * Return a list containing enabled alerts
     * @return Return a list of alerts
     */
    public synchronized List<Alert> getEnabledAlerts()
    {
        List<Alert> x = new ArrayList<Alert>();
        for(Alert a : listAlerts)
            if (a.isEnabled()) x.add(a);
        return x;
    }

    /**
     * Return a list containing disabled alerts
     * @return Return a list of alerts
     */
    public synchronized List<Alert> getDisabledAlerts()
    {
        List<Alert> x = new ArrayList<Alert>();
        for(Alert a : listAlerts)
            if (!a.isEnabled()) x.add(a);
        return x;
    }

    public synchronized void save(Context context)
    {
        try {
            File file = new File(context.getFilesDir(), "listAlert.json");
            if (file.exists())
            {
                file.delete();
                file.createNewFile();
            }

            FileOutputStream outputStream;
            outputStream = context.openFileOutput("listAlert.json", Context.MODE_PRIVATE);
            AlertSerializer alertSerializer = new AlertSerializer();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Alert.class, alertSerializer);
            Gson gson = gsonBuilder.create();

            String json = gson.toJson(listAlerts);
            Log.i("cool", json);
            outputStream.write(json.getBytes());
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void load(Context context) {
        try {
            listAlerts.clear();
            FileInputStream fis = context.openFileInput("listAlert.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            AlertDeserializer alertDeserializer = new AlertDeserializer();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Alert.class, alertDeserializer);
            Gson gson = gsonBuilder.create();
            Type listAlertType = new TypeToken<ArrayList<Alert>>(){}.getType();
            if ((line = bufferedReader.readLine()) != null) {
                ArrayList<Alert> alertsLoaded =  gson.fromJson(line, listAlertType);
                listAlerts.addAll(alertsLoaded);
            }
            Log.i("cool","load");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("cool",e.getMessage());
        }
    }
}
