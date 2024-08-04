package dev.swapi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class TallestCharacterInLatestFilmTest extends BaseTest {

    @Test
    public void testTallestCharacterInLatestFilm() {
        Response response = RestAssured.get("/films/");
        JSONArray films = new JSONObject(response.asString()).getJSONArray("results");

        JSONObject latestFilm = null;
        String latestDate = "0000-00-00";

        for (int i = 0; i < films.length(); i++) {
            JSONObject film = films.getJSONObject(i);
            String releaseDate = film.getString("release_date");
            if (releaseDate.compareTo(latestDate) > 0) {
                latestDate = releaseDate;
                latestFilm = film;
            }
        }

        if (latestFilm == null) {
            throw new RuntimeException("No films found.");
        }

        JSONArray characters = latestFilm.getJSONArray("characters");

        JSONObject tallestCharacter = null;
        int tallestHeight = 0;

        for (int i = 0; i < characters.length(); i++) {
            String url = characters.getString(i);
            JSONObject character = new JSONObject(RestAssured.get(url).asString());
            String heightStr = character.getString("height");
            int height = heightStr.equals("unknown") ? 0 : Integer.parseInt(heightStr);
            if (height > tallestHeight) {
                tallestHeight = height;
                tallestCharacter = character;
            }
        }

        if (tallestCharacter != null) {
            System.out.println("Tallest character in the latest film: " + tallestCharacter.getString("name"));
        } else {
            System.out.println("No characters found.");
        }
    }
}
