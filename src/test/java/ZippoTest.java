import POJO.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.Argument;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @Test
    public void test() {

        given()
// hazırlık kısmı (token, send body, parametreler)
                .when()
// link ve method u veriyoruz
                .then()
// assertion ve verileri ele alma extract
        ;
    }

    @Test
    public void statusCodeTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .statusCode(200)

        ;
    }

    @Test
    public void contentTypeTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)

        ;
    }


    @Test
    public void checkstateresponsebody() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("country", equalTo("United States"))
                .statusCode(200)
                .contentType(ContentType.JSON)

        ;
    }

    @Test
    public void bodyJsonPathTest2() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/01000")
                .then()
                .log().body()
                .body("places[0].state", equalTo("California"))
                .statusCode(200)
                .contentType(ContentType.JSON)

        ;
    }

    @Test
    public void bodyArrayhasSizeTest() {
// verilen pathteki listin size ı kaç elemanı var
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places", hasSize(1))
                .statusCode(200)
                .contentType(ContentType.JSON)

        ;
    }

    @Test
    public void CombineTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places", hasSize(1))
                .body("places.states", hasItem("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .statusCode(200)
                .contentType(ContentType.JSON)

        ;
    }

    @Test
    public void pathparamtest() {

        given()

                .pathParam("Country", "us")
                .pathParam("Zipcode", "90210")
                .log().uri()
                .when()
                .get("http://api.zippopotam.us/{Country}/{Zipcode}")
                .then()
                .log().body()

                .statusCode(200)
                .contentType(ContentType.JSON)

        ;
    }


    @Test
    public void pathparamtest2() {

        for (int i = 90210; i <= 90213; i++) {


            given()

                    .pathParam("Country", "us")
                    .pathParam("Zipcode", i)
                    .log().uri()
                    .when()
                    .get("http://api.zippopotam.us/{Country}/{Zipcode}")
                    .then()
                    .log().body()
                    .body("places", hasSize(1))

                    .statusCode(200)
                    .contentType(ContentType.JSON)

            ;
        }

    }


    @Test
    public void QueryparamTest() {

        given()

                .param("page", 1)

                .log().uri()
                .when()
                .get("https://gorest.co.in/public/v1/users")
                .then()
                .log().body()

                .statusCode(200)
                .contentType(ContentType.JSON)

        ;
    }

    @Test
    public void QueryparamTest2() {

        for (int i = 1; i <= 10; i++) {


            given()

                    .param("page", i)

                    .log().uri()
                    .when()
                    .get("https://gorest.co.in/public/v1/users")
                    .then()
                    .log().body()
                    .body("meta.pagination.page", equalTo(i))

                    .statusCode(200)
                    .contentType(ContentType.JSON)

            ;
        }

    }


    RequestSpecification RequestSpecs;
    ResponseSpecification ResponseSpecs;

    @BeforeClass
    void Setup() {
        baseURI = "https://gorest.co.in/public/v1";
        RequestSpecs = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();

        ResponseSpecs = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.BODY)
                .build();
    }

    @Test
    public void requestResponseSpecification() {

        given()

                .param("page", 1)
                .spec(RequestSpecs)
                .when()
                .get("/users")
                .then()
                .log().body()
                .body("meta.pagination.page", equalTo(1))
                .spec(ResponseSpecs)
        ;

    }


    @Test
    public void ExtractJsonPath() {

        String placeName =
                given()

                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().path("places[0].'place name'")
                // extract metodu ile given ile başlayan satır, değer döndürür hale geliyor. en sonda extract olmalı

                ;

        System.out.println(placeName);

    }

    @Test
    public void ExtractJsonPathInt() {
        int limit =

                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("meta.pagination.limit");

        System.out.println("Limit= " + limit);
        Assert.assertEquals(limit, 10, "test sonucu");


    }

    @Test
    public void ExtractJsonPathInt2() {
        int id =

                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data[2].id");

        System.out.println("ID= " + id);


    }

    @Test
    public void ExtractJsonPathInt3() {
        List<Integer> Ids =

                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data.id");

        System.out.println("IDs= " + Ids);
        Assert.assertTrue(Ids.contains(3045));

    }

    @Test
    public void ExtractJsonPathName() {
        List<String> Names =

                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data.name");

        System.out.println("Names= " + Names);
        Assert.assertTrue(Names.contains("Navin Patil"));

    }

    @Test
    public void ExtractJsonPathResponse() {
        Response body =

                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().response();

        List<String> name = body.path("data.name");
        List<Integer> Ids = body.path("data.id");
        int limit = body.path("meta.pagination.limit");

        System.out.println(name);
        System.out.println(Ids);
        System.out.println(limit);

    }

    @Test
    public void extractingJsonPOJO() {
Location yer=

            given()

                    .pathParam("Country", "us")

                    .log().uri()
                    .when()
                    .get("http://api.zippopotam.us/{Country}/90210")
                    .then()
                    .extract().as(Location.class)

            ;

        System.out.println("yer= "+yer);
        System.out.println("yer.getcountry"+yer.getCountry());
        System.out.println(    "yer.getPlaces"+ yer.getPlaces().get(0).getPlacename());

    }





}



