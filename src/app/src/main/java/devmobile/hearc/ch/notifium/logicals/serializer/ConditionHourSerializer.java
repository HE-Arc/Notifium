package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionHour;

public class ConditionHourSerializer implements JsonSerializer<ConditionHour> {
    @Override
    public JsonElement serialize(ConditionHour cond, Type ConditionHour, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("dateCondition", cond.getDateCondition().toString());
        return object;
    }
}
