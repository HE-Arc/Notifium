package devmobile.hearc.ch.notifium.logicals.conditions;

import java.time.DayOfWeek;
import java.time.LocalDate;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

/**
 * Implements Condition_I
 * Used to raise an alert at a given day of the week
 */
public class ConditionDateDayOfWeek implements Condition_I {

    private DayOfWeek dayOfWeek;

    public ConditionDateDayOfWeek(DayOfWeek day)
    {
        dayOfWeek = day;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    @Override
    public boolean evaluatePredicate() {
        return LocalDate.now().getDayOfWeek() == dayOfWeek;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.DateDayOfWeek;
    }

    @Override
    public int hashCode() {
        return (super.hashCode() << 16) ^ (dayOfWeek.hashCode() & 65535);
    }
}

