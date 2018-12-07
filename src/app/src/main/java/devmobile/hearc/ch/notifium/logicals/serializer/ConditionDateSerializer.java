package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDate;

public class ConditionDateSerializer implements JsonSerializer<ConditionDate> {
    @Override
    public JsonElement serialize(ConditionDate cond, Type ConditionDate, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("threshold", cond.getDateCondition().toString());
        return object;
    }
}
