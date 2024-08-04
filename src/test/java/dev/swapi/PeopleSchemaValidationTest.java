package dev.swapi;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

public class PeopleSchemaValidationTest extends BaseTest {

    @Test
    public void validatePeopleSchema() {
        RestAssured.get("/people/")
                .then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("people-schema.json"));
    }
}

