package com.farhan.badanus.model;

public class ItemModel {
    private String nama, pesanan, idItem, noWa;
    public ItemModel(){

    }
    public ItemModel(String nama, String pesanan, String noWa, String id){
        this.nama = nama;
        this.pesanan = pesanan;
        this.noWa = noWa;
        this.idItem = id;
    }

    public String getIdItem() {
        return idItem;
    }

    public String getNoWa() {
        return noWa;
    }

    public void setNoWa(String noWa) {
        this.noWa = noWa;
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
