package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.Order;
import pojo.OrderTrack;

import static io.restassured.RestAssured.given;

public class OrdersClient {

    private final static String API_PATH = "/api/v1/orders";

    @Step("Create order")
    public static Response apiCreateOrder(Order order) {
        return given().header("Content-Type","application/json").body(order)
                .post(API_PATH);
    }

    @Step("Get order list")
    public static Response apiCetOrdersList() {
        return given().header("Content-Type","application/json")
                .get(API_PATH);
    }

    @Step("Delete order")
    public static void apiDeleteOrder(OrderTrack someTrack) {
        given().header("Content-Type","application/json")
                .put((API_PATH + "/cancel?track=" + someTrack.getTrack()));
    }

    @Step("Get order track")
    public static OrderTrack apiGetOrderTrack(Response response) {
      return response.body().as(OrderTrack.class);
    }
}
