package devmobile.hearc.ch.notifium.logicals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import devmobile.hearc.ch.notifium.logicals.conditions.Condition_I;
import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;


public class Trigger extends ArrayList<Condition_I> {

    public boolean evaluate() {
        boolean b = false;
        for (Condition_I c : this)
            b &= c.evaluatePredicate();
        return b;
    }

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

