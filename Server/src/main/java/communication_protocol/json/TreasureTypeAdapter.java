package communication_protocol.json;

import com.google.gson.*;
import data_structures.treasure.Treasure;

import java.lang.reflect.Type;

public class TreasureTypeAdapter implements JsonSerializer<Treasure.Type>, JsonDeserializer<Treasure.Type> {

	@Override
	public JsonElement serialize(Treasure.Type src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
		System.err.println(src.getClass().getSimpleName());
		result.add("properties", context.serialize(src, src.getClass()));

		return result;
	}

	@Override
	public Treasure.Type deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		String type = jsonObject.get("type").getAsString();
		JsonElement element = jsonObject.get("properties");

		try {
			return context.deserialize(element, Class.forName("data_structures.treasure." + type));
		} catch (ClassNotFoundException cnfe) {
			throw new JsonParseException("Unknown element type: " + type, cnfe);
		}
	}
}
