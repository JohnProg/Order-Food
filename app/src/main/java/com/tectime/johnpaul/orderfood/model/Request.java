package com.tectime.johnpaul.orderfood.model;

import java.util.List;

/**
 * Created by johnmachahuay on 4/1/18.
 */

public class Request {
    private String id;
    private String phone;
    private String name;
    private String address;
    private String total;
    private String status;
    private List<Order> orders;

    public Request() {
    }

    public final static String STATUS_PLACED = "0";
    public final static String STATUS_SHIPPING = "1";
    public final static String STATUS_SHIPPED = "2";

    public Request(String phone, String name, String address, String total, List<Order> orders) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.orders = orders;
        this.status = STATUS_PLACED;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
