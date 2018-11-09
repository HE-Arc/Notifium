package devmobile.hearc.ch.notifium;

import java.util.ArrayList;
import java.util.List;

import devmobile.hearc.ch.notifium.logicals.Alert;

public class AlertStorage {
    private final List<Alert> listAlerts;

    public AlertStorage()
    {
        listAlerts = new ArrayList<Alert>();
    }

    public void addAlert(Alert alert)
    {
        listAlerts.add(alert);
    }

    public void removeAlert(Alert alert)
    {
        listAlerts.remove(alert);
    }

    public Alert getAlert(int index)
    {
        return listAlerts.get(index);
    }

    public Alert getAlert(String name) {
        for(Alert a : listAlerts)
            if(a.getName() == name) return a;
        return null;
    }

    public List<Alert> getAlerts()
    {
        return listAlerts;
    }

    public List<Alert> getEnabledAlerts()
    {
        List<Alert> x = new ArrayList<Alert>();
        for(Alert a : listAlerts)
            if (a.isEnabled()) x.add(a);
        return x;
    }

    public List<Alert> getDisabledAlerts()
    {
        List<Alert> x = new ArrayList<Alert>();
        for(Alert a : listAlerts)
            if (!a.isEnabled()) x.add(a);
        return x;
    }
}
