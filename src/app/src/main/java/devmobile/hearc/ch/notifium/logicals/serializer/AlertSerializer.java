package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.Alert;
import devmobile.hearc.ch.notifium.logicals.Trigger;

/**
 * Serializer for an alert
 */
public class AlertSerializer implements JsonSerializer<Alert> {

    @Override
    public JsonElement serialize(Alert alert, Type Alert, JsonSerializationContext context) {

        JsonObject object = new JsonObject();
        TriggerSerializer triggerSerializer = new TriggerSerializer();

        object.addProperty("name", alert.getName() );
        object.addProperty("notification", alert.getNotification()); // this is the text for the notification
        object.addProperty("isEnabled", alert.isEnabled());

        // Stock triggers as an array to loop on on the load
        // Because there is no unique identifier in a trigger
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < alert.size(); ++i)
        {
            Trigger trigger = alert.get(i);
            jsonArray.add(triggerSerializer.serialize(trigger, Trigger.class, context));
        }

        object.add("triggers", jsonArray);
        return object;
    }
}
