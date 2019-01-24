/**
 * Projet : Notifium
 * Students : Raphaël Margueron / Fabien Mottier / Segan Salomon
 * Teacher : Aicha Rizzotti
 * Module : 3255.1-Developpement_mobile
 * Repository Git : https://github.com/HE-Arc/Notifium
 * Date : 25.01.2019
 */
package devmobile.hearc.ch.notifium.logicals.conditions;

import java.time.LocalDate;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

/**
 * Implements Condition_I
 * Used to raise an alert at a given date
 */
public class ConditionDate implements Condition_I {

    private LocalDate dateCondition;

    public ConditionDate(int day, int month, int year)
    {
        this(LocalDate.of(year, month, day));
    }

    public ConditionDate(LocalDate date)
    {
        dateCondition = date;
    }

    /**
     * Evaluate current date with this condition date
     * @return True if now is after this condition date
     */
    public boolean evaluatePredicate()
    {
        return LocalDate.now().isAfter(dateCondition) || LocalDate.now().isEqual(dateCondition);
    }

    public LocalDate getDateCondition() {
        return dateCondition;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.Date;
    }

    @Override
    public int hashCode() {
        return (super.hashCode() << 16) ^ (dateCondition.hashCode() & 65535);
    }
}

