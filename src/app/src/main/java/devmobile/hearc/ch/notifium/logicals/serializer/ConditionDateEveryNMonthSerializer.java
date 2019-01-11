package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDateEveryNMonth;
import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class ConditionDateEveryNMonthSerializer implements JsonSerializer<ConditionDateEveryNMonth> {
    @Override
    public JsonElement serialize(ConditionDateEveryNMonth cond, Type conditionType, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("type", ConditionType.DateEveryNDay.name());
        object.addProperty("dayOfMonth", cond.getDayOfMonth());
        object.addProperty("month", cond.getMonth());
        object.addProperty("dt", cond.getDt());
        return object;
    }
}
