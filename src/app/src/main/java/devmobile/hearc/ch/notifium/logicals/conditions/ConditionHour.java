package devmobile.hearc.ch.notifium.logicals.conditions;

import java.util.Date;

public class ConditionHour implements Condition_I {
    private Date dateCondition;

    public ConditionHour(int hours, int minutes)
    {
        dateCondition = new Date(0,0,0, hours, minutes);
    }

    /**
     * Evaluate current hour with this condition hour
     * @return True if now is after this condition hour
     */
    public boolean evaluatePredicate()
    {
        Date now = new Date();
        if(now.getHours() >= dateCondition.getHours())
        {
            if(now.getMinutes() >= dateCondition.getMinutes())
                return true;
        }
        return false;
    }
}
