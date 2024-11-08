import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GET_Read {

    static {
        baseURI = "https://petstore.swagger.io/v2/pet";
    }

    @Test
    public void testCreatePetPositive() {
        String petData = """
            {
                "id": 123456,
                "category": {"id": 0, "name": "dog"},
                "name": "Buddy",
                "photoUrls": ["string"],
                "tags": [{"id": 0, "name": "friendly"}],
                "status": "available"
            }
        """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(petData)
                .when()
                .post();

        response.then()
                .statusCode(200)
                .body("id", equalTo(123456))
                .body("name", equalTo("Buddy"));
    }

    @Test
    public void testCreatePet403Forbidden() {
        String petData = """
            {
                "id": 987654,
                "category": {"id": 1, "name": "dog"},
                "name": "UnauthorizedPet",
                "photoUrls": ["string"],
                "tags": [{"id": 1, "name": "test"}],
                "status": "available"
            }
        """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(petData)
                .when()
                .post();

        assertEquals(403, response.statusCode(), "Expected 403 Forbidden status code");
    }
}
