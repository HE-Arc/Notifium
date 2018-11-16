package devmobile.hearc.ch.notifium.logicals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import devmobile.hearc.ch.notifium.logicals.conditions.Condition_I;
import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class Alert extends ArrayList<Trigger> {
    private String name;
    private boolean bEnabled;

    public Alert(String name)
    {
        this(name, true);
    }

    public Alert(String name, boolean enabled)
    {
        this.name = name;
        bEnabled = enabled;
    }

    public String getName() { return name; }

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

}
