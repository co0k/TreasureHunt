package communication_controller.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data_structures.treasure.Treasure;

public class JsonConstructor {
	private Gson gson;

	public JsonConstructor() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Treasure.Type.class, new TreasureAbstractAdapter<Treasure.Type>());
		builder.registerTypeAdapter(Treasure.Content.class, new TreasureAbstractAdapter<Treasure.Content>());
		// for nicer debugging, should be commented out in the production version
		builder.setPrettyPrinting();
		builder.serializeNulls();

		gson = builder.create();
	}

	public String toJson(Object src) {
		return gson.toJson(src);
	}

	public <T> T fromJson(String src, Class<T> classOf) {
		return gson.fromJson(src, classOf);
	}

}
