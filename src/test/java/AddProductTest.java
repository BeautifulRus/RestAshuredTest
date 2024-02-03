import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;


public class AddProductTest {

    List<String> names = List.of("Апельсин", "Капуста", "Помидор", "Яблоко", "Daniel991 09 D", "");
    List<String> types = List.of("FRUIT", "VEGETABLE", "VEGETABLE", "FRUIT", "VEGETABLE", "FRUIT");
    List<Boolean> listExotic = List.of(true, false, false, false, true, false);

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
                .body("{\n" +
                        "  \"name\": \"Daniel991 09 D\",\n" +
                        "  \"type\": \"VEGETABLE\",\n" +
                        "  \"exotic\": true\n" +
                        "}")
                .post("api/food")
                .then().extract().response();
        response.then().assertThat().statusCode(200);
        String cookie = response.getCookie("JSESSIONID");
        given()
                .baseUri("http://localhost:8080/")
                .sessionId(cookie)
                .contentType(ContentType.JSON)
                .accept("*/*")
                .body("{\n" +
                        "  \"name\": \"\",\n" +
                        "  \"type\": \"FRUIT\",\n" +
                        "  \"exotic\": false\n" +
                        "}")
                .post("api/food")
                .then().extract().response();
        response.then().assertThat().statusCode(200);



        Response response1 = given()
                .contentType(ContentType.JSON)
                .sessionId(cookie)
                .baseUri("http://localhost:8080/")
                .when()
                .get("api/food")
                .then()
                .extract().response();
        response1.then().assertThat().statusCode(200);
        response1.jsonPath().getList("", Pojo.class);

        for (int i = 0;i<6;i++)
        {
            //огромное условие
            if (response1.jsonPath().getList("", Pojo.class)
                    //что мы можем сделать с этим листом?
                    .get(i)
                    //начинаем сверять значения
                    .getName().equals(names.get(i)))
            {//если да, мы ведём проверку дальше
                if (response1.jsonPath().getList("", Pojo.class)
                        //что мы можем сделать с этим листом?
                        .get(i)
                        //начинаем сверять значения
                        .getType().equals(types.get(i)))
                {//если да, мы ведём проверку дальше
                    if (response1.jsonPath().getList("", Pojo.class)
                            //что мы можем сделать с этим листом?
                            .get(i)
                            //начинаем сверять значения
                            .getExotic() == listExotic.get(i))
                    {/*если да, то поджик с индексом i прошёл проверку*/
                    }/*если listExotic не соответствует значению поджика*/ else Assertions.fail();


                }/*если types не соответствует значению поджика*/ else Assertions.fail();



            }/*если names не соответствует значению поджика*/  else Assertions.fail();




        }






        int u = 8;

    }


}
