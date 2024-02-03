import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class AddProductTest {



    @Test
    @Tags({
            @Tag("Smoke"),
            @Tag("REST_API"),
            @Tag("Test1")
    })
    void AddProductToTable() {

        Response response = given()
                .baseUri("http://localhost:8080/")
                .contentType(ContentType.JSON)
                .accept("*/*")
                .relaxedHTTPSValidation()
                .body("{\n" +
                        "  \"name\": \"Daniel991 09 D\",\n" +
                        "  \"type\": \"VEGETABLE\",\n" +
                        "  \"exotic\": true\n" +
                        "}")
                .post("api/food")
                .then().extract().response();
        response.then().assertThat().statusCode(200);
        String cookie = response.getCookie("JSESSIONID");

        List<Pojo> pojoList = given()
                .contentType(ContentType.JSON)
                .sessionId(cookie)
                .baseUri("http://localhost:8080/")
                .when()
                .get("api/food")
                .then()
                .extract()

                .jsonPath().getList("Food", Pojo.class);





       // System.out.println(response1.getBody().prettyPrint());
        System.out.println(Pojo.class);
        int i = 8;

    }


}
