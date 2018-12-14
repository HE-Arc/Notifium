package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDate;
import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class ConditionDateSerializer implements JsonSerializer<ConditionDate> {
    @Override
    public JsonElement serialize(ConditionDate cond, Type ConditionDate, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("type", ConditionType.Date.name());
        object.addProperty("year", cond.getDateCondition().getYear());
        object.addProperty("month", cond.getDateCondition().getMonthValue());
        object.addProperty("day", cond.getDateCondition().getDayOfMonth());
        return object;
    }
}
