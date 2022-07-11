import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
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
    public void createNewOrder(){
        Order order = new Order(color);
        Response response = given().header("Content-Type","application/json").body(order)
                .post("/api/v1/orders");
        response.then().statusCode(201).and()
                .assertThat().body("track", greaterThan(0));
        someTrack = response.body().as(OrderTrack.class);
    }

    @After
    public void deletingTestData(){
        given().header("Content-Type","application/json")
                .put(("/api/v1/orders/cancel?track=" + someTrack.getTrack()));
    }
}
