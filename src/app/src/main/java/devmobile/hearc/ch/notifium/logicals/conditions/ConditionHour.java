package devmobile.hearc.ch.notifium.logicals.conditions;

import java.time.LocalTime;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class ConditionHour implements Condition_I {
    private LocalTime dateCondition;

    public ConditionHour(int hours, int minutes)
    {
        dateCondition = LocalTime.of(hours, minutes, 0);
    }

    /**
     * Evaluate current hour with this condition hour
     * @return True if now is after this condition hour
     */
    public boolean evaluatePredicate()
    {
        LocalTime now = LocalTime.now();
        if(now.getHour() >= dateCondition.getHour())
        {
            if(now.getMinute() >= dateCondition.getMinute())
                return true;
        }
        return false;
    }

    public LocalTime getDateCondition() {
        return dateCondition;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.Hour;
    }
}

