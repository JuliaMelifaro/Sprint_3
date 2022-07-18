import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.Order;
import pojo.OrderTrack;

import java.util.ArrayList;

import static api.client.OrdersClient.*;
import static org.hamcrest.Matchers.greaterThan;

 @RunWith(Parameterized.class)
public class CreationOrderTest {

     String colorElement;
     ArrayList<String> color = new ArrayList<>();

     OrderTrack someTrack;

     public CreationOrderTest(String colorElement) {
         color.add(colorElement);
     }

    @Parameterized.Parameters
    public static Object[] getTestData(){
        return new Object[][] {
                {"BLACK"},
                {"GREY"},
                {"BLACK, GREY"},
                {null}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI="http://qa-scooter.praktikum-services.ru";

    }

    @Test
    @DisplayName("Creating order with different transport color - parameterized")
    public void createNewOrder(){
        Order order = new Order(color);
        Response response = apiCreateOrder(order);
        response.then().statusCode(201).and()
                .assertThat().body("track", greaterThan(0));
        someTrack = apiGetOrderTrack(response);
    }

    @After
    public void deletingTestData(){
         apiDeleteOrder(someTrack);
    }
}
