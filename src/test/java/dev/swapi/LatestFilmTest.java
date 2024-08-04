package dev.swapi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class LatestFilmTest extends BaseTest {

    @Test
    public void testLatestFilm() {
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

        if (latestFilm != null) {
            System.out.println("Latest film: " + latestFilm.getString("title"));
        } else {
            System.out.println("No films found.");
        }
    }
}
