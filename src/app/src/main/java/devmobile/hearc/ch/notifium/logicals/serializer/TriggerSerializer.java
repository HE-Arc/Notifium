/**
 * Projet : Notifium
 * Students : Raphaël Margueron / Fabien Mottier / Segan Salomon
 * Teacher : Aicha Rizzotti
 * Module : 3255.1-Developpement_mobile
 * Repository Git : https://github.com/HE-Arc/Notifium
 * Date : 25.01.2019
 */
package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.Trigger;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionBatteryLevel;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDate;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDateDayOfWeek;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionHour;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionLocalisation;
import devmobile.hearc.ch.notifium.logicals.conditions.Condition_I;

/**
 * Serializer for a trigger
 */
public class TriggerSerializer implements JsonSerializer<Trigger> {

    @Override
    public JsonElement serialize(Trigger trigger, Type Trigger, JsonSerializationContext context) {

        ConditionLocalisationSerializer conditionLocalisationSerializer = new ConditionLocalisationSerializer();
        ConditionBatteryLevelSerializer conditionBatteryLevelSerializer = new ConditionBatteryLevelSerializer();
        ConditionDateSerializer conditionDateSerializer = new ConditionDateSerializer();
        ConditionDaySerializer conditionDaySerializer = new ConditionDaySerializer();
        ConditionHourSerializer conditionHourSerializer = new ConditionHourSerializer();

        JsonObject object = new JsonObject();

        // Stock conditions as an array to loop on on the load
        // Because there is no unique identifier in a condition
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < trigger.size(); ++i) {
            Condition_I cond = trigger.get(i);
            switch (cond.getConditionType())
            {
                case Position:
                    jsonArray.add(conditionLocalisationSerializer.serialize((ConditionLocalisation) cond, ConditionLocalisation.class, context));
                    break;
                case Date:
                    jsonArray.add(conditionDateSerializer.serialize((ConditionDate) cond, ConditionDate.class, context));
                    break;
                case DateDayOfWeek:
                    jsonArray.add(conditionDaySerializer.serialize((ConditionDateDayOfWeek) cond, ConditionDateDayOfWeek.class, context));
                    break;
                case Hour:
                    jsonArray.add(conditionHourSerializer.serialize((ConditionHour) cond, ConditionHour.class, context));
                    break;
                case Battery:
                    jsonArray.add(conditionBatteryLevelSerializer.serialize((ConditionBatteryLevel) cond, ConditionBatteryLevel.class, context));
                    break;
            }
        }

        object.add("conditions", jsonArray);
        return object;
    }
}
