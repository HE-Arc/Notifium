package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDate;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionDateEveryNYear;

public class ConditionDateEveryNYearDeserializer implements JsonDeserializer<ConditionDateEveryNYear> {
    @Override
    public ConditionDateEveryNYear deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        int dayOfYear = object.get("dayOfYear").getAsInt();
        int year = object.get("year").getAsInt();
        int dt = object.get("dt").getAsInt();

        // generate the localdate
        LocalDate l = LocalDate.ofYearDay(year, dayOfYear);

        return new ConditionDateEveryNYear(l, dt);
    }
}
