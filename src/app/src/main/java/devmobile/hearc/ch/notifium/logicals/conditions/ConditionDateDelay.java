package devmobile.hearc.ch.notifium.logicals.conditions;

import java.time.LocalDate;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class ConditionDateDelay implements Condition_I {

    private LocalDate dateCondition;
    private int delay;

    /**
     *
     * @param day
     * @param month
     * @param year
     * @param delay Every n number of day
     */
    public ConditionDateDelay(int day, int month, int year, int delay)
    {
        this.dateCondition = LocalDate.of(year, month, day);
        this.delay = delay;
    }

    /**
     *
     * @param delay Every n number of day
     */
    public ConditionDateDelay(int delay)
    {
        this.dateCondition = LocalDate.now();
        this.delay = delay;
    }

    /**
     * Evaluate current date with this condition date
     * @return True if now is after this condition date
     */
    public boolean evaluatePredicate()
    {
        // TODO: If now is delay further than dateCondition, updateDateCOndition and reevaluate
        return LocalDate.now().isAfter(dateCondition);
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.DateDelay;
    }

}
