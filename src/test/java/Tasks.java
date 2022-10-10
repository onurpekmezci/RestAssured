import POJO.Location;
import POJO.Task4;
import POJO.Users;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import javafx.beans.binding.When;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Tasks {
    /**
     * Task 6
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * Converting Into POJO
     */

    @Test
    public void extractingJsonPOJO() {
        Users userr =

                given()


                        .log().uri()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")
                        .then()
                        .extract().as(Users.class);
        System.out.println(userr.getUserId());
        System.out.println(userr.getId());
        System.out.println(userr.getTitle());


    }

    /**
     * Task 2
     * create a request to https://httpstat.us/203
     * expect status 203
     * expect content type TEXT
     */

    @Test
    public void controltext() {
        given()


                .log().uri()
                .when()
                .get("https://httpstat.us/203")
                .then()
                .contentType(ContentType.TEXT)
                .statusCode(203)


        ;

    }

    /**
     * Task 3
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     * expect title in response body to be "quis ut nam facilis et officia qui"
     */

    @Test
    public void Task3() {

        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("quis ut nam facilis et officia qui"))
        ;


    }

    /**
     * Task 4
     * create a request to https://jsonplaceholder.typicode.com/todos
     * expect status 200
     * expect content type JSON
     * expect third item have:
     * title = "fugiat veniam minus"
     * userId = 1
     */


    @Test
    public void task4() {

        Response tt =

        given()
                .log().uri()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos")

                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .extract().response();

        List<Integer> userId = tt.path("userId");
        List<String> title = tt.path("title");


        Assert.assertTrue(userId.contains(1));
        Assert.assertTrue(title.contains("fugiat veniam minus"));


       }


}
