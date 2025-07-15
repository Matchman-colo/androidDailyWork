package com.example.e_5_3;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;

public class MainApplication extends Application {
    private int cartCount = 0;
    private ArrayList<CartInfo> cartList = new ArrayList<>();

    public int getCartCount() {
        return cartCount;
    }

    public void setCartCount(int cartCount) {
        this.cartCount = cartCount;
    }

    public ArrayList<CartInfo> getCartList() {
        return cartList;
    }
    public interface CartObserver {
        void onCartUpdated();
    }

    private List<CartObserver> observers = new ArrayList<>();

    public void addObserver(CartObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(CartObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (CartObserver observer : observers) {
            observer.onCartUpdated();
        }
    }
    /**
     * 添加商品到购物车
     * @param goodsId 商品ID
     * @param name 商品名
     * @param price 商品价格
     * @param picPath 商品图片资源ID
     */
    // 1. 修改 addToCart 方法签名，增加描述参数
    public void addToCart(int goodsId, String name, float price, int picPath, String desc) {
        boolean found = false;
        for (CartInfo item : cartList) {
            if (item.goodsId == goodsId) {
                item.count++;
                found = true;
                break;
            }
        }
        if (!found) {
            int newId = cartList.size() + 1;
            CartInfo newItem = new CartInfo(newId, goodsId, name, price, 1, picPath);
            newItem.desc = desc;  // 给描述赋值
            cartList.add(newItem);
        }
        updateCartCount();
    }


    private void updateCartCount() {
        int totalCount = 0;
        for (CartInfo item : cartList) {
            totalCount += item.count;
        }
        cartCount = totalCount;
        notifyObservers();
    }
}
