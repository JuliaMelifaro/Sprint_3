import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Courier;

import static api.client.CourierClient.*;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {
    Courier courierData;

    @Before
    public void setUp() {
        RestAssured.baseURI="http://qa-scooter.praktikum-services.ru";
        courierData = new Courier("Runner" + Math.random()*1000, "qwerty" + Math.random()*10);
        apiCreateCourier(courierData);
    }

    @Test
    @DisplayName("Login of existing courier")
    public void courierLoginSuccessful(){
        apiCreateCourier(courierData);
        apiLoginCourier(courierData).then().statusCode(200).and()
              .assertThat().body("id", greaterThan(0));
    }


    @Test
    @DisplayName("Trying to login without login")
    public void courierLoginWithoutLogin() {
        Courier courierWithoutLogin = new Courier(null, courierData.getPassword());
        apiLoginCourier(courierWithoutLogin).then().statusCode(400).and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Trying to login without password")
    public void courierLoginWithoutPassword() {
        Courier courierWithoutPassword = new Courier(courierData.getLogin(), "");
        apiLoginCourier(courierWithoutPassword).then().statusCode(400).and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    @DisplayName("Trying to login with not existing courier")
    public void courierLoginWrongData() {
        Courier courierDataWrong = new Courier("notExist" + Math.random()*2000, "qwerty" + Math.random()*10);
        apiLoginCourier(courierDataWrong).then().statusCode(404).and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void deletingTestData(){
        apiDeleteCourier(courierData);
    }

}