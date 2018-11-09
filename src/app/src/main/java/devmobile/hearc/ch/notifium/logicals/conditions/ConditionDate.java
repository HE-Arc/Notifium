package devmobile.hearc.ch.notifium.logicals.conditions;

import java.time.LocalDate;

public class ConditionDate implements Condition_I {

    private LocalDate dateCondition;

    public ConditionDate(int day, int month, int year)
    {
        dateCondition = LocalDate.of(year, month, day);
    }

    /**
     * Evaluate current date with this condition date
     * @return True if now is after this condition date
     */
    public boolean evaluatePredicate()
    {
        return LocalDate.now().isAfter(dateCondition);
    }
}
