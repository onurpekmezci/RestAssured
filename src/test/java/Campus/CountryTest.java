package Campus;

import Campus.Model.Country;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matcher;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountryTest {
    Cookies cookies;
    @BeforeClass
    public void LoginCampus() {
        baseURI = "https://demo.mersys.io/";
        Map<String,String> credential = new HashMap<>();
        credential.put("username","richfield.edu");
        credential.put("password","Richfield2020!");
        credential.put("rememberMe","true");
       // {"username": "richfield.edu","password": "Richfield2020!","rememberMe": "true"}

        cookies=
        given()
                .contentType(ContentType.JSON)
                .body(credential)

                .when()
                .post("auth/login")

                .then()
                .log().all()
                .statusCode(200)
                .extract().response().getDetailedCookies();



    }

    String CountryID;
    String CountryName;
    String CountryCode;

    @Test
    public void createCountry()
    {

        CountryName=GetRandomName()+"oo";
        CountryCode=GetRandomCode()+"oo";

        Country country = new Country();
        country.setName(CountryName);
        country.setCode(CountryCode);

        CountryID =
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)


                .when()
                .post("school-service/api/countries")


                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("id")

        ;

    }

    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegative()
    {


        Country country = new Country();
        country.setName(CountryName);
        country.setCode(CountryCode);


                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(country)


                        .when()
                        .post("school-service/api/countries")


                        .then()
                        .log().body()
                        .statusCode(400)
                        .body("message",equalTo("The Country with Name \""+CountryName+"\" already exists."))

        ;


    }

    @Test(dependsOnMethods = "createCountryNegative")
    public void updateCountry()
    {

        CountryName=GetRandomName()+"oo";
        CountryCode=GetRandomCode()+"oo";

        Country country = new Country();
        country.setName(CountryName);
        country.setCode(CountryCode);
        country.setId(CountryID);


                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(country)


                        .when()
                        .put("school-service/api/countries")


                        .then()
                        .log().body()
                        .statusCode(200)
                       // .extract().jsonPath().getString("id")

        ;

    }

    @Test(dependsOnMethods = "updateCountry")
    public void deleteCountry()
    {

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("CountryID",CountryID)


                .when()
                .delete("school-service/api/countries/{CountryID}")


                .then()
                .statusCode(200)
        // .extract().jsonPath().getString("id")

        ;

    }

    @Test(dependsOnMethods = "deleteCountry")
    public void updateCountryNegative()
    {

        CountryName=GetRandomName()+"oo";
        CountryCode=GetRandomCode()+"oo";

        Country country = new Country();
        country.setName(CountryName);
        country.setCode(CountryCode);
        country.setId(CountryID);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)


                .when()
                .put("school-service/api/countries")


                .then()
                .log().body()
                .statusCode(400)
        // .extract().jsonPath().getString("id")

        ;

    }


    @Test(dependsOnMethods = "updateCountryNegative")
    public void deleteCountryNegative()
    {

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("CountryID",CountryID)


                .when()
                .delete("school-service/api/countries/{CountryID}")


                .then()
                .statusCode(400)
                .body("message",equalTo("Country not found"))


        ;

    }

    public static String GetRandomName() {

        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }
    public static String GetRandomCode() {

        return RandomStringUtils.randomAlphabetic(5).toLowerCase();
    }
}
