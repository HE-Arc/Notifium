package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionHour;
import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

/**
 * Serializer for a condition to check a time
 */
public class ConditionHourSerializer implements JsonSerializer<ConditionHour> {
    @Override
    public JsonElement serialize(ConditionHour cond, Type ConditionHour, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("type", ConditionType.Hour.name()); // use to retrieve the type on load
        object.addProperty("hours", cond.getDateCondition().getHour());
        object.addProperty("minutes", cond.getDateCondition().getMinute());
        return object;
    }
}
