package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionBatteryLevel;

/**
 * Deserializer for a condition to check the level of the batter
 */
public class ConditionBatteryLevelDeserializer implements JsonDeserializer<ConditionBatteryLevel> {
    @Override
    public ConditionBatteryLevel deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        int threshold = object.get("threshold").getAsInt();

        return new ConditionBatteryLevel(threshold);
    }
}
