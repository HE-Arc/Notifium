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
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.Trigger;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionBatteryLevel;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDate;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDateDayOfWeek;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionHour;
import devmobile.hearc.ch.notifium.logicals.conditions.ConditionLocalisation;
import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class TriggerDeserializer implements JsonDeserializer<Trigger> {

    @Override
    public Trigger deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        Trigger trigger = new Trigger();

        ConditionLocalisationDeserializer conditionLocalisationDeserializer = new ConditionLocalisationDeserializer();
        ConditionBatteryLevelDeserializer conditionBatteryLevelDeserializer = new ConditionBatteryLevelDeserializer();
        ConditionDateDeserializer conditionDateDeserializer = new ConditionDateDeserializer();
        ConditionDayDeserializer conditionDayDeserializer = new ConditionDayDeserializer();
        ConditionHourDeserializer conditionHourDeserializer = new ConditionHourDeserializer();

        JsonArray conditions = object.get("conditions").getAsJsonArray();
        for (JsonElement condition:conditions) {
            String conditionTypeName = condition.getAsJsonObject().get("type").getAsString();
            ConditionType conditionType = ConditionType.valueOf(conditionTypeName);
            switch (conditionType)
            {
                case Position:
                    trigger.add(conditionLocalisationDeserializer.deserialize(condition, ConditionLocalisation.class, context));
                    break;
                case Date:
                    trigger.add(conditionDateDeserializer.deserialize(condition, ConditionDate.class, context));
                    break;
                case DateDayOfWeek:
                    trigger.add(conditionDayDeserializer.deserialize(condition, ConditionDateDayOfWeek.class, context));
                    break;
                case Hour:
                    trigger.add(conditionHourDeserializer.deserialize(condition, ConditionHour.class, context));
                    break;
                case Battery:
                    trigger.add(conditionBatteryLevelDeserializer.deserialize(condition, ConditionBatteryLevel.class, context));
                    break;
            }
        }

        return trigger;
    }
}
