package devmobile.hearc.ch.notifium.logicals.conditions;

import java.time.LocalDate;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class ConditionDateEveryNDay implements Condition_I {

    private int dayOfYear;
    private int dt;

    public int getDayOfYear() {return dayOfYear;}
    public int getDt() {return dt;}

    /**
     * Set a condition from the given date, every n day it will return true
     * @param date
     * @param dt Every n number of day
     */
    public ConditionDateEveryNDay(LocalDate date, int dt)
    {
        this.dayOfYear = date.getDayOfYear();
        this.dt = dt;
    }

    /**
     * Set a condition from now, every n day it will return true
     * @param dt Every n number of day
     */
    public ConditionDateEveryNDay(int dt)
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
        int currentDayOfYear = now.getDayOfYear();

        if(currentDayOfYear == dayOfYear)
            return true;
        else if(currentDayOfYear > dayOfYear)
        {
            int diff = currentDayOfYear - dayOfYear;
            return diff % dt == 0;
        }
        else
            return false;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.DateEveryNDay;
    }

}
