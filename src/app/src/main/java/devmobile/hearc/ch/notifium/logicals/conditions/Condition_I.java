package devmobile.hearc.ch.notifium.logicals.conditions;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

/**
 * Contract that every condition must be fulfilling
 */
public interface Condition_I {
    public boolean evaluatePredicate();
    public ConditionType getConditionType();
}

