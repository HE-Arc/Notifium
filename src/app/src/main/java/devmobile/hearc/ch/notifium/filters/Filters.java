/**
 * Projet : Notifium
 * Students : Raphaël Margueron / Fabien Mottier / Segan Salomon
 * Teacher : Aicha Rizzotti
 * Module : 3255.1-Developpement_mobile
 * Repository Git : https://github.com/HE-Arc/Notifium
 * Date : 25.01.2019
 */
package devmobile.hearc.ch.notifium.filters;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

/**
 * Static final class containing every filters that can be applied to the alertStorage
 */
public final class Filters {

    /**
     * Filter : all alerts
     */
    public static final Set<ConditionType> ALL = Collections.unmodifiableSet(
            new HashSet<ConditionType>(Arrays.asList(
                    ConditionType.Position,
                    ConditionType.Date,
                    ConditionType.DateDelay,
                    ConditionType.DateEveryNDay,
                    ConditionType.DateEveryNMonth,
                    ConditionType.DateEveryNYear,
                    ConditionType.DateDayOfWeek,
                    ConditionType.DateDayOfTheMonth,
                    ConditionType.Hour,
                    ConditionType.Battery
                )
            )
    );

    /**
     * Filter : only positioned alerts
     */
    public static final Set<ConditionType> POSITION = Collections.unmodifiableSet(
            new HashSet<ConditionType>(Arrays.asList(
                    ConditionType.Position
                )
            )
    );

    /**
     * Filter : only timed alerts [Date, Day, Hour]
     */
    public static final Set<ConditionType> TIME = Collections.unmodifiableSet(
            new HashSet<ConditionType>(Arrays.asList(
                    ConditionType.Date,
                    ConditionType.DateDelay,
                    ConditionType.DateEveryNDay,
                    ConditionType.DateEveryNMonth,
                    ConditionType.DateEveryNYear,
                    ConditionType.DateDayOfWeek,
                    ConditionType.DateDayOfTheMonth,
                    ConditionType.Hour
                )
            )
    );

    /**
     * Filter : only battery alerts
     */
    public static final Set<ConditionType> BATTERY = Collections.unmodifiableSet(
            new HashSet<ConditionType>(Arrays.asList(
                    ConditionType.Battery
                )
            )
    );

}
