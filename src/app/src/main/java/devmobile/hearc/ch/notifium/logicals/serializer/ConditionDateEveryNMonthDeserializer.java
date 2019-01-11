package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDate;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDateEveryNMonth;

public class ConditionDateEveryNMonthDeserializer implements JsonDeserializer<ConditionDateEveryNMonth> {
    @Override
    public ConditionDateEveryNMonth deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        int dayOfMonth = object.get("dayOfMonth").getAsInt();
        int month = object.get("month").getAsInt();
        int dt = object.get("dt").getAsInt();

        // generate the localdate
        LocalDate l = LocalDate.of(LocalDate.now().getYear(), month, dayOfMonth);

        return new ConditionDateEveryNMonth(l, dt);
    }
}
