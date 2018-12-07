package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.Alert;
import devmobile.hearc.ch.notifium.logicals.Trigger;

public class AlertSerializer implements JsonSerializer<Alert> {

    @Override
    public JsonElement serialize(Alert alert, Type Alert, JsonSerializationContext context) {

        JsonObject object = new JsonObject();
        TriggerSerializer triggerSerializer = new TriggerSerializer();

        object.addProperty("name", alert.getName() );
        object.addProperty("isEnabled", alert.isEnabled());

        for (int i = 0; i < alert.size(); ++i)
        {
            Trigger trigger = alert.get(i);
            object.add(Integer.toString(i), triggerSerializer.serialize(trigger, Trigger.class, context));
        }

        return object;
    }
}
