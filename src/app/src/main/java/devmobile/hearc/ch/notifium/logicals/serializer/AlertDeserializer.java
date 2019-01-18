package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.Alert;
import devmobile.hearc.ch.notifium.logicals.Trigger;

/**
 * Deserializer for an alert
 */
public class AlertDeserializer implements JsonDeserializer<Alert> {

    @Override
    public Alert deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        String name = object.get("name").getAsString();
        String notification = object.get("notification").getAsString(); // this is the text for the notification
        boolean isEnabled = object.get("isEnabled").getAsBoolean();

        Alert alert = new Alert(name, notification, isEnabled);
        TriggerDeserializer triggerDeserializer = new TriggerDeserializer();

        // deserialize an array of trigger
        JsonArray triggers = object.get("triggers").getAsJsonArray();
        for (JsonElement trigger:triggers) {
            alert.add(triggerDeserializer.deserialize(trigger.getAsJsonObject(), Trigger.class, context));
        }

        return alert;
    }
}
