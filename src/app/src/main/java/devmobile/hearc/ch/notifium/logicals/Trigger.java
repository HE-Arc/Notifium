package devmobile.hearc.ch.notifium.logicals;

import java.util.ArrayList;

import devmobile.hearc.ch.notifium.logicals.conditions.Condition_I;

public class Trigger extends ArrayList<Condition_I> {

    public boolean evaluate() {
        boolean b = false;
        for (Condition_I c : this)
            b &= c.evaluatePredicate();
        return b;
    }
}
