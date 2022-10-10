package GoRest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.collections.Maps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class GoRestUsersTest {

    @BeforeTest
    void Setup() {
        baseURI = "https://gorest.co.in/public/v2/";
    }

    @Test
    public void createUser() {


        // 1. way with class
        User newUser = new User();
        newUser.setName("tryzzzs");
        newUser.setEmail(GetRandomEmail().toLowerCase());
        newUser.setGender("male");
        newUser.setStatus("active");

        //2. way
//        Map<String,String> users = new HashMap<>();
//        users.put("name","tryzzzee");
//        users.put("gender","male");
//        users.put("email",GetRandomEmail().toLowerCase());
//        users.put("status","active");

        userID =
                given()
                        // token, gidecek body, parametreler
                        .header("Authorization", "Bearer 0ff0e90adbfcc42f91a5ce93525c1bff554c5fc74f32026f231a9e3d79784ff4")
                        .contentType(ContentType.JSON)
                        .body(newUser)

                        .when()
                        .post("users")


                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        // .extract().path("id")
                        .extract().jsonPath().getInt("id")
        ;
// path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.
        System.out.println(userID);
    }

    int userID;

    @Test(dependsOnMethods = "createUser")
    public void updateUser() {


        Map<String, String> newUser = new HashMap<>();
        newUser.put("name", "tryzzzeeee");

        given()
                // token, gidecek body, parametreler
                .header("Authorization", "Bearer 0ff0e90adbfcc42f91a5ce93525c1bff554c5fc74f32026f231a9e3d79784ff4")
                .contentType(ContentType.JSON)
                .body(newUser)
                .log().body()
                .pathParam("userID", userID)
                .when()

                .put("users/{userID}")


                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo("tryzzzeeee"))

        ;

        System.out.println(userID);
    }

    @Test
    public void getUserID() {


        given()
                // token, gidecek body, parametreler
                .header("Authorization", "Bearer 0ff0e90adbfcc42f91a5ce93525c1bff554c5fc74f32026f231a9e3d79784ff4")
                .contentType(ContentType.JSON)

                .log().body()
                .pathParam("userID", userID)
                .when()

                .get("users/{userID}")


                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(userID))

        ;

        System.out.println(userID);
    }

    @Test
    public void getUserV1() {

Response response=
        given()
                // token, gidecek body, parametreler
                .header("Authorization", "Bearer 0ff0e90adbfcc42f91a5ce93525c1bff554c5fc74f32026f231a9e3d79784ff4")


                .when()

                .get("https://gorest.co.in/public/v1/users")


                .then()

                .statusCode(200)
                .extract().response()

        ;

//response.as();  // tüm gelen response uygun nesnelerin yaılması gerekiyor
        List<User> datauser = response.jsonPath().getList("data",User.class); //jsonpath bir response içindeki bir parçayı nesneye dönüştürebilrisiniz.

        System.out.println(datauser);


        // Daha önceki örneklerde (as) Clas dönüşümleri için tüm yapıya karşılık gelen
        // gereken tüm classları yazarak dönüştürüp istediğimiz elemanlara ulaşıyorduk.
        // Burada ise(JsonPath) aradaki bir veriyi clasa dönüştürerek bir list olarak almamıza
        // imkan veren JSONPATH i kullandık.Böylece tek class ise veri alınmış oldu
        // diğer class lara gerek kalmadan

        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.

    }

    @Test
    public void getUserIDExtract() {

        User usss =
                given()
                        // token, gidecek body, parametreler
                        .header("Authorization", "Bearer 0ff0e90adbfcc42f91a5ce93525c1bff554c5fc74f32026f231a9e3d79784ff4")
                        .contentType(ContentType.JSON)
                        .pathParam("userID", 3589)
                        .when()
                        .get("users/{userID}")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().as(User.class);
                       // .extract().jsonPath().getObject("", User.class);

        System.out.println(usss);
    }


    @Test(dependsOnMethods = "createUser", priority = 3)
    public void deletebyUserID() {


        given()
                // token, gidecek body, parametreler
                .header("Authorization", "Bearer 0ff0e90adbfcc42f91a5ce93525c1bff554c5fc74f32026f231a9e3d79784ff4")
                .contentType(ContentType.JSON)

                .log().body()
                .pathParam("userID", userID)
                .when()

                .delete("users/{userID}")


                .then()
                .log().body()
                .statusCode(204)


        ;


    }

    @Test
    public void getUser() {

        Response response =

                given()
                        // token, gidecek body, parametreler
                        .header("Authorization", "Bearer 0ff0e90adbfcc42f91a5ce93525c1bff554c5fc74f32026f231a9e3d79784ff4")
                        .contentType(ContentType.JSON)

                        .log().body()

                        .when()

                        .get("users")


                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response();


        //  get 3. user id (path and jsonpath)
        int iduser3path = response.path("[2].id");
        int iduser3jsonpath = response.jsonPath().getInt("[2].id");
        // set all data to class
        // getuserbtıd test set to class
        User[] userspath= response.as(User[].class);
        System.out.println(Arrays.toString(userspath));

        // jsonpath
     List<User> userajson  = response.jsonPath().getList("",User.class);
        System.out.println(userajson);
    }

    @Test(dependsOnMethods = "deletebyUserID")
    public void deletebyUserIDnegative() {


        given()
                // token, gidecek body, parametreler
                .header("Authorization", "Bearer 0ff0e90adbfcc42f91a5ce93525c1bff554c5fc74f32026f231a9e3d79784ff4")
                .contentType(ContentType.JSON)

                .log().body()
                .pathParam("userID", userID)
                .when()

                .delete("users/{userID}")


                .then()
                .log().body()
                .statusCode(404)


        ;


    }


    public static String GetRandomEmail() {

        return RandomStringUtils.randomAlphabetic(8) + "@gmail.com";
    }

}

class User {

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String gender;
    private String email;
    private String status;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}