package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.DayOfWeek;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDay;

public class ConditionDayDeserializer implements JsonDeserializer<ConditionDay> {
    @Override
    public ConditionDay deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        DayOfWeek dayOfWeek = DayOfWeek.of(object.get("dayOfWeek").getAsInt());

        return new ConditionDay(dayOfWeek);
    }
}
