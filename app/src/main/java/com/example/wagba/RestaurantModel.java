package com.example.wagba;

public class RestaurantModel {
    String restaurantName;
    String restaurantCategory;
    float restaurantRating;
    int restaurantImage;

    public RestaurantModel(String restaurantName,
                           String restaurantCategory,
                           float restaurantRating,
                           int restaurantImage) {
        this.restaurantName = restaurantName;
        this.restaurantCategory = restaurantCategory;
        this.restaurantRating = restaurantRating;
        this.restaurantImage = restaurantImage;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantCategory() {
        return restaurantCategory;
    }

    public float getRestaurantRating() {
        return restaurantRating;
    }

    public int getRestaurantImage() {
        return restaurantImage;
    }
}
