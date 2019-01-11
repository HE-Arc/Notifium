package devmobile.hearc.ch.notifium.filters;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public final class Filters {

    /**
     * Filter : all alerts
     */
    public static final Set<ConditionType> ALL = Collections.unmodifiableSet(
            new HashSet<ConditionType>(Arrays.asList(
                    ConditionType.Position,
                    ConditionType.Date,
                    ConditionType.DateDayOfWeek,
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
                    ConditionType.DateDayOfWeek,
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
