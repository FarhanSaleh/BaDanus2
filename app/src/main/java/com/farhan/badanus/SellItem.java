package com.farhan.badanus;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SellItem implements Parcelable {
    private String sellName;
    private int sellCount;
    private int sellPrice;


    public void setSellName(String sellName) {
        this.sellName = sellName;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getSellName() {
        return sellName;
    }

    public int getSellCount() {
        return sellCount;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    SellItem(){

    }
    private SellItem(Parcel in) {
        this.sellName = in.readString();
        this.sellCount = in.readInt();
        this.sellPrice = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sellName);
        dest.writeInt(this.sellCount);
        dest.writeInt(this.sellPrice);
    }
    public static final Creator<SellItem> CREATOR = new Creator<SellItem>() {
        @Override
        public SellItem createFromParcel(Parcel in) {
            return new SellItem(in);
        }

        @Override
        public SellItem[] newArray(int size) {
            return new SellItem[size];
        }
    };
}
