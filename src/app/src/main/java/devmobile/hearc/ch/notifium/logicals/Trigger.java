package devmobile.hearc.ch.notifium.logicals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import devmobile.hearc.ch.notifium.logicals.conditions.Condition_I;
import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

/**
 * This class provides a smart way to promote conditions
 * A trigger can be raised by as many condition as wanted
 * The background service must be running for the conditions
 * to be triggered
 * Triggers performs OR operation between themselves in alert
 * Conditions performs AND operation between themselves in trigger
 */
public class Trigger extends ArrayList<Condition_I> {

    /**
     * Evaluate every condition contained in this trigger
     * @return True if all trigger are evaluated as true
     */
    public boolean evaluate() {
        boolean b = false;
        for (Condition_I c : this)
            b &= c.evaluatePredicate();
        return b;
    }

    /**
     * Fetch every conditions to get their type
     * @return a set containing every type of condition contained in this trigger
     */
    public Set<ConditionType> getConditionsTypes()
    {
        Set<ConditionType> types = new HashSet<>(ConditionType.values().length);
        for (Condition_I c : this)
            types.add(c.getConditionType());
        return types;
    }

    @Override
    public int hashCode() {
        return (super.hashCode() << 16) ^ (getConditionsTypes().hashCode() & 65535);
    }
}

