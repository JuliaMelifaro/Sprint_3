import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Courier;

import static api.client.CourierClient.apiCreateCourier;
import static api.client.CourierClient.apiDeleteCourier;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreationCourierTest {
    Courier courierData;

    @Before
    public void setUp() {
        RestAssured.baseURI="http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Creating new courier with entering all parameters")
    public void createNewCourierAllParameters(){
        courierData = new Courier("Runner" + Math.random()*1000, "qwerty" + Math.random()*1000,
                "Forest" + Math.random()*1000);
        apiCreateCourier(courierData).then().statusCode(201).and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Trying to create courier by duplicating data")
    public void canCreateOnlyOriginalCourier(){
        courierData = new Courier("Runner" + Math.random()*1000, "qwerty" + Math.random()*1000,
                "Forest" + Math.random()*1000);
        apiCreateCourier(courierData);
        apiCreateCourier(courierData).then().assertThat().body("code", equalTo(409)).and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Creating new courier with entering only needed parameters")
    public void createNewCourierAOnlyNeededParameters() {
        courierData = new Courier("Runner" + Math.random()*1000, "qwerty" + Math.random()*1000);
        apiCreateCourier(courierData).then().statusCode(201).and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Trying to create courier without login")
    public void creationCourierWithoutLogin(){
        courierData = new Courier(null, "qwerty" + Math.random()*1000);
        apiCreateCourier(courierData).then().statusCode(400).and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Trying to create courier without password")
    public void creationCourierWithoutPassword(){
        courierData = new Courier("Runner" + Math.random()*1000, "");
        apiCreateCourier(courierData).then().statusCode(400).and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void deletingTestData(){
        apiDeleteCourier(courierData);
    }

}