package devmobile.hearc.ch.notifium.logicals.conditions;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class ConditionDateDayOfWeek implements Condition_I {

    private DayOfWeek dayOfWeek;

    public ConditionDateDayOfWeek(DayOfWeek day)
    {
        dayOfWeek = day;
    }

    @Override
    public boolean evaluatePredicate() {
        return LocalDate.now().getDayOfWeek() == dayOfWeek;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.DateDayOfWeek;
    }
}
