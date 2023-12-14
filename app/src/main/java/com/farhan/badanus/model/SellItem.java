package com.farhan.badanus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SellItem{
    private String sellName, sellDate, sellId;


    public SellItem(){

    }
    public SellItem(String sellName, String sellDate, String sellId){
        this.sellName = sellName;
        this.sellDate = sellDate;
        this.sellId = sellId;
    }

    public String getSellName() {
        return sellName;
    }

    public void setSellName(String sellName) {
        this.sellName = sellName;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
    }
}
