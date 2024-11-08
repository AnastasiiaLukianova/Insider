import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class PUT_Update
{

    static {
        baseURI = "https://petstore.swagger.io/v2/pet";
    }

    @Test
    public void testUpdatePetPositive() {
        String updatedPetData = """
            {
                "id": 123456,
                "category": {"id": 0, "name": "dog"},
                "name": "Max",
                "photoUrls": ["string"],
                "tags": [{"id": 0, "name": "friendly"}],
                "status": "available"
            }
        """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(updatedPetData)
                .when()
                .put();

        response.then()
                .statusCode(200)
                .body("name", equalTo("Max"));
    }

    @Test
    public void testUpdatePetNegative() {
        String invalidPetData = """
            {
                "id": 123456,
                "name": ""
            }
        """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(invalidPetData)
                .when()
                .put();

        response.then()
                .statusCode(400);
    }

}
