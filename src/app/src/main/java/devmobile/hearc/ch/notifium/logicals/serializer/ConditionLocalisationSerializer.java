/**
 * Projet : Notifium
 * Students : Raphaël Margueron / Fabien Mottier / Segan Salomon
 * Teacher : Aicha Rizzotti
 * Module : 3255.1-Developpement_mobile
 * Repository Git : https://github.com/HE-Arc/Notifium
 * Date : 25.01.2019
 */
package devmobile.hearc.ch.notifium.logicals.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import devmobile.hearc.ch.notifium.logicals.conditions.ConditionLocalisation;
import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class ConditionLocalisationSerializer implements JsonSerializer<ConditionLocalisation> {
    @Override
    public JsonElement serialize(ConditionLocalisation cond, Type ConditionLocalisation, JsonSerializationContext context)
    {
        JsonObject object = new JsonObject();
        object.addProperty("type", ConditionType.Position.name());
        object.addProperty("lat", cond.getLat());
        object.addProperty("lng", cond.getLng());
        object.addProperty("radius", cond.getRadius());
        return object;
    }
}
