import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Order;
import pojo.OrderTrack;

import static api.client.OrdersClient.*;
import static org.hamcrest.Matchers.*;

public class OrderListTest {
    OrderTrack someTrack;

    @Before
    public void setUp() {
        RestAssured.baseURI="http://qa-scooter.praktikum-services.ru";
        Order order = new Order();
        Response response = apiCreateOrder(order);
        someTrack = apiGetOrderTrack(response);
    }

    @Test
    @DisplayName("Getting order list")
    public void getOrderList() {
        apiCetOrdersList().then().statusCode(200).and()
                .assertThat().body("orders", not(emptyArray()));
    }

    @After
    public void deletingTestData(){
        apiDeleteOrder(someTrack);
    }
}
