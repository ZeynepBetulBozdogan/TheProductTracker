package com.example.myapplication.model.request;

public class ItemRequest {
    String itemcode;

    public String getItemcode() {
        return itemcode;
    }

    public ItemRequest(String itemcode) {
        this.itemcode = itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }
}
