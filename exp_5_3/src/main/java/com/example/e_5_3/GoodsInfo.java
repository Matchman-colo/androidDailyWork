package com.example.e_5_3;

import java.io.Serializable;

public class GoodsInfo implements Serializable {
    public int id;               // 商品编号
    public String name;         // 商品名称
    public String description;  // 商品描述
    public float price;         // 商品价格
    public int picPath;      // 商品图片路径

    // 无参构造函数
    public GoodsInfo() {
    }

    // 带参构造函数
    public GoodsInfo(int id, String name, String description, float price, int picPath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.picPath = picPath;
    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", picPath='" + picPath + '\'' +
                '}';
    }
}
