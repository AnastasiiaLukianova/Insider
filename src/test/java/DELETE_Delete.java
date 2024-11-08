import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DELETE_Delete
{

    static {
        baseURI = "https://petstore.swagger.io/v2/pet";
    }

    @Test
    public void testDeletePetPositive() {
        int petId = 123456;

        Response response = given()
                .when()
                .delete("/" + petId);

        response.then()
                .statusCode(200);
    }

    @Test
    public void testDeletePetNegative() {
        int nonExistentId = 999999;

        Response response = given()
                .when()
                .delete("/" + nonExistentId);

        response.then()
                .statusCode(404);
    }

}
