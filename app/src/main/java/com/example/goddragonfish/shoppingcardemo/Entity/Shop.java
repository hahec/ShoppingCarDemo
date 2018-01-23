package com.example.goddragonfish.shoppingcardemo.Entity;

import java.util.List;

/**
 * Created by GodDragonFish on 2017/12/20.
 */

public class Shop {

    boolean ShopCh;
    String shopName;
    List<Item> itemList;

    public Shop(boolean shopCh) {
        ShopCh = shopCh;
    }

    public boolean getShopCh(){
        return ShopCh;
    }

    public boolean isShopCh() {
        return ShopCh;
    }

    public void setShopCh(boolean shopCh) {
        ShopCh = shopCh;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }


}
