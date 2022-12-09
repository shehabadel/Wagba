package com.example.wagba.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RestaurantModel implements Parcelable {
    String restaurantName;
    String restaurantCategory;
    float restaurantRating;
    int restaurantImage;
    ArrayList<DishModel> restaurantDishes = new ArrayList<>();
    public RestaurantModel(String restaurantName,
                           String restaurantCategory,
                           float restaurantRating,
                           int restaurantImage) {
        this.restaurantName = restaurantName;
        this.restaurantCategory = restaurantCategory;
        this.restaurantRating = restaurantRating;
        this.restaurantImage = restaurantImage;
    }


    protected RestaurantModel(Parcel in) {
        restaurantName = in.readString();
        restaurantCategory = in.readString();
        restaurantRating = in.readFloat();
        restaurantImage = in.readInt();
        restaurantDishes = in.createTypedArrayList(DishModel.CREATOR);
    }

    public static final Creator<RestaurantModel> CREATOR = new Creator<RestaurantModel>() {
        @Override
        public RestaurantModel createFromParcel(Parcel in) {
            return new RestaurantModel(in);
        }

        @Override
        public RestaurantModel[] newArray(int size) {
            return new RestaurantModel[size];
        }
    };

    public RestaurantModel(String restaurantName, String restaurantCategory, float restaurantRating, int restaurantImage, ArrayList<DishModel> restaurantDishes) {
        this.restaurantName = restaurantName;
        this.restaurantCategory = restaurantCategory;
        this.restaurantRating = restaurantRating;
        this.restaurantImage = restaurantImage;
        ArrayList<DishModel> copiedDishModel = new ArrayList<>();
        copiedDishModel.addAll(restaurantDishes);
        this.restaurantDishes = copiedDishModel;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(restaurantName);
        parcel.writeString(restaurantCategory);
        parcel.writeFloat(restaurantRating);
        parcel.writeInt(restaurantImage);
        parcel.writeTypedList(restaurantDishes);
    }

    public ArrayList<DishModel> getRestaurantDishes() {
        return restaurantDishes;
    }

    public void setRestaurantDishes(ArrayList<DishModel> restaurantDishes) {
        this.restaurantDishes = restaurantDishes;
    }
}
