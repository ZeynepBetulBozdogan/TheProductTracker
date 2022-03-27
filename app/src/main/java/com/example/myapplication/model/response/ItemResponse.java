package com.example.myapplication.model.response;

import java.io.Serializable;



public class ItemResponse implements Serializable
{

    private String itemBarCode;
    private String itemId;
    private String itemName;
    private String price;
    private String message;
    private String status;



    public ItemResponse(String itemBarCode, String itemId, String itemName, String price, String message, String status) {
        super();
        this.itemBarCode = itemBarCode;
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.message = message;
        this.status = status;
    }

    public String getItemBarCode() {
        return itemBarCode;
    }

    public void setItemBarCode(String itemBarCode) {
        this.itemBarCode = itemBarCode;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}