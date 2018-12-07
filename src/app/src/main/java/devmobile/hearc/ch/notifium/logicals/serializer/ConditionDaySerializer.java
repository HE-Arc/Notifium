package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDay;

public class ConditionDaySerializer implements JsonSerializer<ConditionDay> {
    @Override
    public JsonElement serialize(ConditionDay cond, Type ConditionDay, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("dayOfWeek", cond.getDayOfWeek().toString());
        return object;
    }
}
