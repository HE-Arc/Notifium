/**
 * Projet : Notifium
 * Students : Raphaël Margueron / Fabien Mottier / Segan Salomon
 * Teacher : Aicha Rizzotti
 * Module : 3255.1-Developpement_mobile
 * Repository Git : https://github.com/HE-Arc/Notifium
 * Date : 25.01.2019
 */
package devmobile.hearc.ch.notifium.logicals.conditions;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

/**
 * Contract that every condition must be fulfilling
 */
public interface Condition_I {
    public boolean evaluatePredicate();
    public ConditionType getConditionType();
}

