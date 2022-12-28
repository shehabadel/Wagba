package com.example.wagba.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.wagba.R;

public class DishModel implements Parcelable {
    String dishID;
    String dishName;
    int dishPrice;

    public void setDishID(String dishID) {
        this.dishID = dishID;
    }

    String dishImage;

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
}
