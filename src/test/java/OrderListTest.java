import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class OrderListTest {
    OrderTrack someTrack;

    @Before
    public void setUp() {
        RestAssured.baseURI="http://qa-scooter.praktikum-services.ru";
        Order order = new Order();
        Response response = given().header("Content-Type","application/json").body(order)
                .post("/api/v1/orders");;
        someTrack = response.body().as(OrderTrack.class);
    }

    @Test
    public void getOrderList() {
        given().header("Content-Type","application/json")
                .get("/api/v1/orders").then().statusCode(200).and()
                .assertThat().body("orders", not(emptyArray()));
    }

    @After
    public void deletingTestData(){
        given().header("Content-Type","application/json")
                .put(("/api/v1/orders/cancel?track=" + someTrack.getTrack()));
    }
}
