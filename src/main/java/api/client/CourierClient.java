package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.Courier;
import pojo.LoginData;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private final static String API_PATH = "/api/v1/courier";

    @Step("Create courier")
    public static Response apiCreateCourier(Courier data) {
        return given().header("Content-Type","application/json").body(data)
                .post(API_PATH);
    }

    @Step("Courier login in")
    public static Response apiLoginCourier(Courier data) {
        return given().header("Content-Type","application/json").body(data)
                .post(API_PATH + "/login");
    }

    @Step("Delete courier")
    public static void apiDeleteCourier(Courier data) {
        LoginData login = apiLoginCourier(data).body().as(LoginData.class);
        given().header("Content-Type","application/json")
                .delete(API_PATH + login.getId());
    }
}
