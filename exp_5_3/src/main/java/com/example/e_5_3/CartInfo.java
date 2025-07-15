package com.example.e_5_3;

import java.io.Serializable;

public class CartInfo implements Serializable {
    public int id;        // 购物车ID
    public int goodsId;   // 商品ID
    public String name;
    public float price;
    public String desc;
    public int count;
    public int picPath;   // 新增字段，商品图片资源ID

    public CartInfo() {}

    public CartInfo(int id, int goodsId, String name, float price, int count, int picPath) {
        this.id = id;
        this.goodsId = goodsId;
        this.name = name;
        this.price = price;
        this.count = count;
        this.picPath = picPath;
    }
}

