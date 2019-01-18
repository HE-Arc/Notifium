/**
 * Projet : Notifium
 * Students : Raphaël Margueron / Fabien Mottier / Segan Salomon
 * Teacher : Aicha Rizzotti
 * Module : 3255.1-Developpement_mobile
 * Repository Git : https://github.com/HE-Arc/Notifium
 * Date : 25.01.2019
 */
package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionHour;

/**
 * Deserializer for a condition to check a time
 */
public class ConditionHourDeserializer implements JsonDeserializer<ConditionHour> {
    @Override
    public ConditionHour deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        int hours = object.get("hours").getAsInt();
        int minutes = object.get("minutes").getAsInt();

        return new ConditionHour(hours, minutes);
    }
}
