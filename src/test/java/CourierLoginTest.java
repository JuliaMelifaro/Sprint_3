import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {
    Courier courierData;

    @Before
    public void setUp() {
        RestAssured.baseURI="http://qa-scooter.praktikum-services.ru";
        courierData = new Courier("Runner" + Math.random()*1000, "qwerty" + Math.random()*10);
        given().header("Content-Type","application/json").body(courierData)
                .post("/api/v1/courier");
    }

    @Test
    public void courierLoginSuccessful(){
        given().header("Content-Type","application/json").body(courierData)
                .post("/api/v1/courier");
        given().header("Content-Type","application/json").body(courierData)
                .post("/api/v1/courier/login").then().statusCode(200).and()
                .assertThat().body("id", greaterThan(0));
    }


    @Test
    public void courierLoginWithoutLogin() {
        Courier courierWithoutLogin = new Courier(null, courierData.getPassword());
        given().header("Content-Type","application/json").body(courierWithoutLogin)
                .post("/api/v1/courier/login").then().statusCode(400).and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void courierLoginWithoutPassword() {
        Courier courierWithoutPassword = new Courier(courierData.getLogin(), "");
        given().header("Content-Type","application/json").body(courierWithoutPassword)
                .post("/api/v1/courier/login").then().statusCode(400).and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    public void courierLoginWrongData() {
        Courier courierDataWrong = new Courier("notExist" + Math.random()*2000, "qwerty" + Math.random()*10);
        given().header("Content-Type", "application/json").body(courierDataWrong)
                .post("/api/v1/courier/login").then().statusCode(404).and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void deletingTestData(){
        LoginData login = given().header("Content-Type","application/json").body(courierData)
                .post("/api/v1/courier/login").body().as(LoginData.class);
        given().header("Content-Type","application/json")
                .delete("/api/v1/courier/" + login.getId());
    }

}