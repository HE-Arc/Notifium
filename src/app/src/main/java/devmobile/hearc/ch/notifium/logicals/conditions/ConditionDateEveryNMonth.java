package devmobile.hearc.ch.notifium.logicals.conditions;

import java.time.LocalDate;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class ConditionDateEveryNMonth implements Condition_I {

    private int month;
    private int dt;

    /**
     * Set a condition from the given date, every n month it will return true
     * @param date
     * @param dt Every n number of month
     */
    public ConditionDateEveryNMonth(LocalDate date, int dt)
    {
        this.month = date.getMonthValue();
        this.dt = dt;
    }

    /**
     * Set a condition from now, every n month it will return true
     * @param dt Every n number of month
     */
    public ConditionDateEveryNMonth(int dt)
    {
        this(LocalDate.now(), dt);
    }

    /**
     * Evaluate current date with this condition date
     * @return True if now is after this condition date
     */
    public boolean evaluatePredicate()
    {
        LocalDate now =  LocalDate.now();
        int currentMonthOfYear = now.getMonthValue();

        if(currentMonthOfYear == month)
            return true;
        else if(currentMonthOfYear > month)
        {
            int diff = currentMonthOfYear - month;
            return diff % dt == 0;
        }
        else
            return false;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.DateEveryNMonth;
    }

    @Override
    public int hashCode() {
        return (super.hashCode() << 16) ^ (month ^ dt & 65535);
    }

}
