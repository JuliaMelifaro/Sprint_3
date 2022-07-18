package pojo;

import java.util.ArrayList;

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private ArrayList<String> color;

    public Order(ArrayList<String> color) {
        this.firstName = "Иван";
        this.lastName = "Иванов";
        this.address = "Москва, Красная площадь";
        this.metroStation = 4;
        this.phone = "+79999999999";
        this.rentTime = 5;
        this.deliveryDate = "2023-06-06";
        this.comment = "take your time";
        this.color = color;
    }

    public Order() {
    }
}
