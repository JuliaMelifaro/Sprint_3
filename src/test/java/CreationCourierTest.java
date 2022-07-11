import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreationCourierTest {
    Courier courierData;

    @Before
    public void setUp() {
        RestAssured.baseURI="http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createNewCourierAllParameters(){
        courierData = new Courier("Runner" + Math.random()*1000, "qwerty" + Math.random()*1000,
                "Forest" + Math.random()*1000);
        given().header("Content-Type","application/json").body(courierData)
                .post("/api/v1/courier").then().statusCode(201).and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    public void canCreateOnlyOriginalCourier(){
        courierData = new Courier("Runner" + Math.random()*1000, "qwerty" + Math.random()*1000,
                "Forest" + Math.random()*1000);
        given().header("Content-Type","application/json").body(courierData)
                .post("/api/v1/courier");
        given().header("Content-Type","application/json").body(courierData)
                .post("/api/v1/courier").then().assertThat().body("code", equalTo(409)).and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void createNewCourierAOnlyNeededParameters() {
        courierData = new Courier("Runner" + Math.random()*1000, "qwerty" + Math.random()*1000);
        given().header("Content-Type","application/json").body(courierData)
                .post("/api/v1/courier").then().statusCode(201).and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    public void creationCourierWithoutLogin(){
        courierData = new Courier(null, "qwerty" + Math.random()*1000);
        given().header("Content-Type","application/json").body(courierData)
                .post("/api/v1/courier").then().statusCode(400).and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void creationCourierWithoutPassword(){
        courierData = new Courier("Runner" + Math.random()*1000, "");
        given().header("Content-Type","application/json").body(courierData)
                .post("/api/v1/courier").then().statusCode(400).and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void deletingTestData(){
        LoginData login = given().header("Content-Type","application/json").body(courierData)
                .post("/api/v1/courier/login").body().as(LoginData.class);
        given().header("Content-Type","application/json")
                .delete("/api/v1/courier/" + login.getId());
    }

}