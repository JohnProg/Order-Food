package com.tectime.johnpaul.orderfood.model;

/**
 * Created by johnmachahuay on 3/31/18.
 */

public class Food {
    private String name, price, description, image, discount, menuId;

    public Food(String name, String price, String description, String image, String discount, String menuId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.discount = discount;
        this.menuId = menuId;
    }

    public Food() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
