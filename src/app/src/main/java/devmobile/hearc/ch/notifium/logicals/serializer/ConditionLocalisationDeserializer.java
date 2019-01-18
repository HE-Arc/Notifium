package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionLocalisation;

/**
 * Deserializer for a condition to check a geolocation
 */
public class ConditionLocalisationDeserializer implements JsonDeserializer<ConditionLocalisation> {
    @Override
    public ConditionLocalisation deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        float lat = object.get("lat").getAsFloat();
        float lng = object.get("lng").getAsFloat();
        float radius = object.get("radius").getAsFloat();

        return new ConditionLocalisation(lat, lng, radius);
    }
}
