package devmobile.hearc.ch.notifium.logicals.conditions;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class ConditionDay implements Condition_I {

    private DayOfWeek dayOfWeek;

    public ConditionDay(DayOfWeek day)
    {
        dayOfWeek = day;
    }

    @Override
    public boolean evaluatePredicate() {
        return LocalDate.now().getDayOfWeek() == dayOfWeek;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.Day;
    }
}
