package devmobile.hearc.ch.notifium.logicals.conditions;

import java.time.LocalTime;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class ConditionHour implements Condition_I {
    private LocalTime timeCondition;

    public ConditionHour(int hours, int minutes)
    {
        this(LocalTime.of(hours, minutes, 0));
    }

    public ConditionHour(LocalTime time)
    {
        timeCondition = time;
    }

    /**
     * Evaluate current hour with this condition hour
     * @return True if now is after this condition hour
     */
    public boolean evaluatePredicate()
    {
        LocalTime now = LocalTime.now();
        if(now.getHour() >= timeCondition.getHour())
        {
            if(now.getMinute() >= timeCondition.getMinute())
                return true;
        }
        return false;
    }

    public LocalTime getDateCondition() {
        return timeCondition;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.Hour;
    }

    @Override
    public int hashCode() {
        return (super.hashCode() << 16) ^ (timeCondition.hashCode() & 65535);
    }
}

