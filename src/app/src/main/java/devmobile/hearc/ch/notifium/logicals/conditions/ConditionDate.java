package devmobile.hearc.ch.notifium.logicals.conditions;

import java.util.Date;

public class ConditionDate implements Condition_I {

    private Date dateCondition;

    public ConditionDate(int day, int month, int year)
    {
        dateCondition = new Date(year, month, day);
    }

    /**
     * Evaluate current date with this condition date
     * @return True if now is after this condition date
     */
    public boolean evaluatePredicate()
    {
        return new Date().after(dateCondition);
    }
}
