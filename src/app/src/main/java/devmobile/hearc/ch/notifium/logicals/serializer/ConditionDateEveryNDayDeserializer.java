package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDate;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDateEveryNDay;

public class ConditionDateEveryNDayDeserializer implements JsonDeserializer<ConditionDateEveryNDay> {
    @Override
    public ConditionDateEveryNDay deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        int dayOfYear = object.get("dayOfYear").getAsInt();
        int dt = object.get("dt").getAsInt();

        return new ConditionDateEveryNDay(LocalDate.ofYearDay(LocalDate.now().getYear(), dayOfYear), dt);
    }
}
