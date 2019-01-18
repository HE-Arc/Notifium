/**
 * Projet : Notifium
 * Students : Raphaël Margueron / Fabien Mottier / Segan Salomon
 * Teacher : Aicha Rizzotti
 * Module : 3255.1-Developpement_mobile
 * Repository Git : https://github.com/HE-Arc/Notifium
 * Date : 25.01.2019
 */
package devmobile.hearc.ch.notifium.logicals;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

/**
 * This class provides a smart way to promote triggers
 * An alert can be raised by as many trigger as wanted
 * The background service must be running for the triggers
 * to be triggered
 * Triggers performs OR operation between themselves in alert
 * Conditions performs AND operation between themselves in trigger
 */
public class Alert extends ArrayList<Trigger> {
    @SerializedName("alert_name")
    private String name;

    @SerializedName("alert_notif")
    private String notification;

    @SerializedName("alert_bEnabled")
    private boolean bEnabled;

    /**
     * Constructor of Alert, enable it by default
     * @param name : String, name of the alert. It will be display in the list.
     * @param notification String, description of the alert.
     */
    public Alert(String name, String notification)
    {
        this(name, notification, true);
    }

    /**
     * Constructor of Alert
     * @param name String, name of the alert. It will be display in the list.
     * @param notification String, description of the alert.
     * @param enabled boolean, enable or not the alert. If set to false, even the evaluate method will always return false
     */
    public Alert(String name, String notification, boolean enabled)
    {
        this.name = name;
        this.notification = notification;
        bEnabled = enabled;
    }

    /**
     * Name of the alert
     * @return
     */
    public String getName() { return name; }

    /**
     * Description of the alert
     * @return
     */
    public String getNotification() { return notification; }

    /**
     * Determines whether the alert should evaluate triggers or not
     * @return
     */
    public boolean isEnabled() { return bEnabled; }

    /**
     * Evaluate every trigger contained in this alert
     * @return True if at least one trigger has been triggered AND the alert is enabled
     */
    public boolean evaluate()
    {
        if(bEnabled) {
            boolean b = false;
            for (Trigger t : this) {
                if (t.evaluate()) {
                    b = true;
                    break;
                }
            }
            return b && bEnabled;
        }
        else
        {
            return false;
        }
    }

    /**
     * Enable the alert
     */
    public void enable()
    {
        bEnabled = true;
    }

    /**
     * Disable the alert
     */
    public void disable()
    {
        bEnabled = false;
    }

    /**
     * Switch the activation state of the alert
     */
    public void toggle()
    {
        bEnabled = !bEnabled;
    }

    /**
     * Fetch every triggers to get their conditions
     * @return a set containing every type of condition contained in this alert
     */
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

