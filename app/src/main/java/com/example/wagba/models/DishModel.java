package com.example.wagba.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.wagba.R;

public class DishModel implements Parcelable {
    String dishName;
    int dishPrice;
    String dishImage;

    public DishModel(String dishName, int dishPrice, String dishImage) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.dishImage = dishImage;
    }

    protected DishModel(Parcel in) {
        dishName = in.readString();
        dishPrice = in.readInt();
        dishImage = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dishName);
        parcel.writeInt(dishPrice);
        parcel.writeString(dishImage);
    }
}
