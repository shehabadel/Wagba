package com.example.wagba.models;

import android.os.Parcel;
import android.os.Parcelable;

public class DishModel implements Parcelable {
    String dishID;
    String dishName;
    int dishPrice;
    boolean dishAvailability;
    String dishImage;
    String restaurantName;
    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setDishPrice(int dishPrice) {
        this.dishPrice = dishPrice;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public boolean isDishAvailability() {
        return dishAvailability;
    }

    public void setDishAvailability(boolean dishAvailability) {
        this.dishAvailability = dishAvailability;
    }

    public DishModel(String dishID, String dishName, int dishPrice, String restaurantName, String dishImage) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.restaurantName = restaurantName;
        this.dishImage = dishImage;
    }

    public DishModel(){}
    public DishModel(String dishName, int dishPrice, String dishImage) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.dishImage = dishImage;
    }



    public DishModel(String dishID, String dishName, int dishPrice, String dishImage) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.dishImage = dishImage;
    }

    protected DishModel(Parcel in) {
        dishID = in.readString();
        dishName = in.readString();
        dishPrice = in.readInt();
        dishImage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dishID);
        dest.writeString(dishName);
        dest.writeInt(dishPrice);
        dest.writeString(dishImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DishModel> CREATOR = new Creator<DishModel>() {
        @Override
        public DishModel createFromParcel(Parcel in) {
            return new DishModel(in);
        }

        @Override
        public DishModel[] newArray(int size) {
            return new DishModel[size];
        }
    };

    public String getDishName() {
        return dishName;
    }

    public int getDishPrice() {
        return dishPrice;
    }

    public String getDishImage() {
        return dishImage;
    }

    public String getDishID() {
        return dishID;
    }
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }


    public void setDishID(String dishID) {
        this.dishID = dishID;
    }
}
