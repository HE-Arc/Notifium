package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.DayOfWeek;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDateDayOfWeek;

/**
 * Deserializer for a condition to check a day of week
 */
public class ConditionDayDeserializer implements JsonDeserializer<ConditionDateDayOfWeek> {
    @Override
    public ConditionDateDayOfWeek deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        DayOfWeek dayOfWeek = DayOfWeek.of(object.get("dayOfWeek").getAsInt());

        return new ConditionDateDayOfWeek(dayOfWeek);
    }
}
