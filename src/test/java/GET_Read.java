import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class GET_Read {

    static {
        baseURI = "https://petstore.swagger.io/v2/pet";
    }

    @Test
    public void testGetPetPositive() {
        int petId = 123456;

        Response response = given()
                .when()
                .get("/" + petId);

        response.then()
                .statusCode(200)
                .body("id", equalTo(petId))
                .body("name", equalTo("Buddy"));
    }

    @Test
    public void testGetPetNegative() {
        int nonExistentId = 999999;

        Response response = given()
                .when()
                .get("/" + nonExistentId);

        response.then()
                .statusCode(404);
    }

}
