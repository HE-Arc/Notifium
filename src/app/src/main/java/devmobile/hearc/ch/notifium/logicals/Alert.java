package devmobile.hearc.ch.notifium.logicals;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class Alert extends ArrayList<Trigger> {
    @SerializedName("alert_name")
    private String name;

    @SerializedName("alert_notif")
    private String notification;

    @SerializedName("alert_bEnabled")
    private boolean bEnabled;

    public Alert(String name, String notification)
    {
        this(name, notification, true);
    }

    public Alert(String name, String notification, boolean enabled)
    {
        this.name = name;
        this.notification = notification;
        bEnabled = enabled;
    }

    public String getName() { return name; }

    public String getNotification() { return notification; }

    public boolean isEnabled() { return bEnabled; }

    /**
     * Evaluate every trigger contained in this alert
     * @return True if at least one trigger has been triggered AND the alert is enabled
     */
    public boolean evaluate()
    {
        boolean b = false;
        for(Trigger t : this)
        {
            if(t.evaluate()) {
                b = true;
                break;
            }
        }
        return b && bEnabled;
    }

    public void enable()
    {
        bEnabled = true;
    }

    public void disable()
    {
        bEnabled = false;
    }

    public void toggle()
    {
        bEnabled = !bEnabled;
    }

    public Set<ConditionType> getConditionsTypes()
    {
        Set<ConditionType> types = new HashSet<>(ConditionType.values().length);
        for (Trigger t : this)
            for(ConditionType ct : t.getConditionsTypes())
                types.add(ct);
        return types;
    }

    @Override
    public int hashCode() {
        return (super.hashCode() << 16) ^ (getConditionsTypes().hashCode() & 65535);
    }

}

