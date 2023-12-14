package com.farhan.badanus.model;

public class ItemModel {
    private String nama, pesanan, idItem;
    public ItemModel(){

    }
    public ItemModel(String nama, String pesanan, String id){
        this.nama = nama;
        this.pesanan = pesanan;
        this.idItem = id;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPesanan() {
        return pesanan;
    }

    public void setPesanan(String pesanan) {
        this.pesanan = pesanan;
    }
}
