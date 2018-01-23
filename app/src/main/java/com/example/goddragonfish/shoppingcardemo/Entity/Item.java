package com.example.goddragonfish.shoppingcardemo.Entity;

import android.widget.ImageView;

/**
 * Created by GodDragonFish on 2017/12/20.
 */

public class Item {

    boolean ItemCh;
    String ItemName;
    String price;
    int imgUrl;
    int count; //etc的num



    public Item(boolean itemCh) {
        ItemCh = itemCh;
    }


    public boolean isItemCh() {
        return ItemCh;
    }

    public void setItemCh(boolean itemCh) {
        ItemCh = itemCh;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
