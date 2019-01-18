package devmobile.hearc.ch.notifium.logicals.conditions;

import java.time.LocalDate;
import java.util.Calendar;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

/**
 * Implements Condition_I
 * Used to raise an alert at a given day during the month
 * If user enter an invalid day like 30 February this condition will never be evaluated as true
 */
public class ConditionDateMonthly implements Condition_I {

    private int day;

    /**
     *
     * @param day
     */
    public ConditionDateMonthly(int day)
    {
        this.day = day;
    }

    /**
     * Evaluate current date with this condition date
     * @return True if now is after this condition date
     */
    public boolean evaluatePredicate()
    {
        LocalDate now =  LocalDate.now();
        int currentDay = now.getDayOfMonth();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();
        int currentDayCount = currentMonth == 2 ? 28 + (currentYear % 4 == 0 ? 1:0) - (currentYear % 100 == 0 ? (currentYear % 400 == 0 ? 0 : 1) : 0) : 31 - (currentMonth-1) % 7 % 2;

        int targetDay = this.day > currentDayCount ? currentDayCount : this.day;

        return targetDay == currentDay;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.DateDayOfTheMonth;
    }

    @Override
    public int hashCode() {
        return (super.hashCode() << 16) ^ (((Integer)day).hashCode() & 65535);
    }

}
