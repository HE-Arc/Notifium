package devmobile.hearc.ch.notifium;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import devmobile.hearc.ch.notifium.activities.ObserverActivity;
import devmobile.hearc.ch.notifium.logicals.Alert;

public class AlertStorage extends Observable {
    private final List<Alert> listAlerts;

    public AlertStorage(ObserverActivity observer)
    {
        listAlerts = new ArrayList<Alert>();
        this.addObserver(observer);
    }

    public synchronized void addAlert(Alert alert)
    {
        listAlerts.add(alert);
        this.notifyAll();
    }

    public synchronized void removeAlert(Alert alert)
    {
        listAlerts.remove(alert);
    }

    public synchronized Alert getAlert(int index)
    {
        return listAlerts.get(index);
    }

    public synchronized Alert getAlert(String name) {
        for(Alert a : listAlerts)
            if(a.getName() == name) return a;
        return null;
    }

    public synchronized List<Alert> getAlerts()
    {
        return listAlerts;
    }

    public synchronized List<Alert> getEnabledAlerts()
    {
        List<Alert> x = new ArrayList<Alert>();
        for(Alert a : listAlerts)
            if (a.isEnabled()) x.add(a);
        return x;
    }

    public synchronized List<Alert> getDisabledAlerts()
    {
        List<Alert> x = new ArrayList<Alert>();
        for(Alert a : listAlerts)
            if (!a.isEnabled()) x.add(a);
        return x;
    }
}
