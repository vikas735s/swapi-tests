package dev.swapi;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TallestCharacterEverTest extends BaseTest {

    @Test
    public void testTallestCharacterEver() {
        List<JSONObject> allCharacters = new ArrayList<>();
        String nextUrl = "/people/";

        while (nextUrl != null && !nextUrl.equals("null")) {
            Response response = RestAssured.get(nextUrl);
            JSONObject jsonResponse = new JSONObject(response.asString());
            JSONArray characters = jsonResponse.getJSONArray("results");
            for (int i = 0; i < characters.length(); i++) {
                allCharacters.add(characters.getJSONObject(i));
            }
            nextUrl = jsonResponse.optString("next", null);
        }

        JSONObject tallestCharacter = null;
        int tallestHeight = 0;

        for (JSONObject character : allCharacters) {
            String heightStr = character.getString("height");
            int height = heightStr.equals("unknown") ? 0 : Integer.parseInt(heightStr);
            if (height > tallestHeight) {
                tallestHeight = height;
                tallestCharacter = character;
            }
        }

        if (tallestCharacter != null) {
            System.out.println("Tallest character ever: " + tallestCharacter.getString("name"));
        } else {
            System.out.println("No characters found.");
        }
    }
}
