package kalamies.dota;

import javax.json.*;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipes {
    private Map<String, List<String>> combinedFrom = new HashMap<>();
    private Map<String, String> combinesTo = new HashMap<>();

    public Recipes(String path) throws IOException {
        readRecipesFromFile(path);
    }

    public Map<String, List<String>> getCombinedFrom() {
        return combinedFrom;
    }

    public Map<String, String> getCombinesTo() {
        return combinesTo;
    }

    private void readRecipesFromFile(String path) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(path)));

        try (JsonReader reader = Json.createReader(new StringReader(content))) {
            JsonObject object = reader.readObject();
            object.forEach((k, v) -> {
                if (v.getValueType() == JsonValue.ValueType.ARRAY) {
                    List<String> recipe = new ArrayList<>();
                    JsonArray array = (JsonArray) v;
                    array.forEach(x -> {
                        recipe.add(((JsonString)x).getString());
                        combinesTo.put(((JsonString) x).getString(), k);
                    });
                    combinedFrom.put(k, recipe);
                }
            });
        }
    }
}
