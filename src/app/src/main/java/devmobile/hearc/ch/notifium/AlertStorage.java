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
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import devmobile.hearc.ch.notifium.filters.Filters;
import devmobile.hearc.ch.notifium.logicals.Alert;
import devmobile.hearc.ch.notifium.logicals.Trigger;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionBatteryLevel;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDateDayOfWeek;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionHour;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionLocalisation;
import devmobile.hearc.ch.notifium.logicals.conditions.Condition_I;
import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;
import devmobile.hearc.ch.notifium.logicals.serializer.AlertDeserializer;
import devmobile.hearc.ch.notifium.logicals.serializer.AlertSerializer;

/**
 * Singleton
 * Used to managed alerts across the whole application
 * filters can be applied to get only some of the alerts
 */
public class AlertStorage extends Observable {

    private final Set<ConditionType> all = new HashSet<ConditionType>();

    /**
     * Constructor of AlertStorage
     * By default the filter will be ALL
     */
    private AlertStorage()
    {
        listAlerts = new ArrayList<Alert>();
        filteredAlerts = new ArrayList<Alert>();

        this.applyFilter(Filters.ALL);

    }

    private static AlertStorage INSTANCE = new AlertStorage();

    /**
     * Return singleton instance
     */
    public static AlertStorage getInstance()
    {   return INSTANCE;
    }

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
     * Seeds the storage with default alert
     */
    public void seed()
    {
        // Hour alerts
        for(int i = 0; i < 10; i++) {
            // Create an alert
            Alert a = new Alert("MyAlert" + i, "description de notification");
            Trigger t = new Trigger();
            Condition_I ch = new ConditionHour(i + 10, 30);
            t.add(ch);
            a.add(t);

            // store it in list
            addAlert(a);
        }

        // Battery alerts
        for(int i = 0; i < 10; i++) {
            // Create an alert
            Alert a = new Alert("MyAlert" + i, "description de notification");
            Trigger t = new Trigger();
            Condition_I c = new ConditionBatteryLevel(i * 10);
            t.add(c);
            a.add(t);

            // store it in list
            addAlert(a);
        }

        // Day alerts
        for(int i = 0; i < 10; i++) {
            // Create an alert
            Alert a = new Alert("MyAlert" + i, "description de notification");
            Trigger t = new Trigger();
            Condition_I c = new ConditionDateDayOfWeek(DayOfWeek.of((i % 7) + 1));
            t.add(c);
            a.add(t);

            // store it in list
            addAlert(a);
        }

        // Location alerts
        for(int i = 0; i < 10; i++) {
            // Create an alert
            Alert a = new Alert("MyAlert" + i, "description de notification");
            Trigger t = new Trigger();
            Condition_I c = new ConditionLocalisation(1, 1, 10);
            t.add(c);
            a.add(t);

            // store it in list
            addAlert(a);
        }

        // Get filtered alerts
        applyFilter(Filters.ALL);
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

    /**
     * Serialize this object
     * @param context
     */
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
            outputStream.write(json.getBytes());
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserialize this object
     * @param context
     */
    public synchronized static ArrayList<Alert> load(Context context) {
        try {
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
                return  gson.fromJson(line, listAlertType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("fabien",e.getMessage());
        }

        return new ArrayList<Alert>();
    }
}
