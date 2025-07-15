package com.example.e_5_3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity {

    private RecyclerView rvCartItems;
    private TextView tvTotalPrice;
    private Button btnClearCart;

    private CartAdapter adapter;
    private ArrayList<CartInfo> cartList = new ArrayList<>();

    private MainApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        rvCartItems = findViewById(R.id.rvCartItems);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnClearCart = findViewById(R.id.btnClearCart);
        //tvCartCount = findViewById(R.id.tvCartCount);

        app = (MainApplication) getApplication();
        app.addObserver(new MainApplication.CartObserver() {
            @Override
            public void onCartUpdated() {
                // 数据更新后刷新购物车列表
                cartList.clear();
                cartList.addAll(app.getCartList());
                adapter.notifyDataSetChanged();
                updateTotalPrice();
                //tvCartCount.setText("购物车: " + app.getCartCount());
            }
        });

        // ✅ 从全局 Application 中获取实际添加过的购物车商品列表
        cartList = new ArrayList<>(app.getCartList());

        adapter = new CartAdapter(cartList, this::onRemoveItemClicked);
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        rvCartItems.setAdapter(adapter);

        updateTotalPrice();

        btnClearCart.setOnClickListener(v -> {
            cartList.clear();
            app.getCartList().clear(); // 同时清除全局的购物车
            adapter.notifyDataSetChanged();
            updateTotalPrice();
            app.setCartCount(0);
            Toast.makeText(this, "购物车已清空", Toast.LENGTH_SHORT).show();
        });
    }

    private void onRemoveItemClicked(int position) {
        CartInfo removed = cartList.remove(position);
        app.getCartList().remove(removed); // 同时移除全局购物车的对应项
        adapter.notifyItemRemoved(position);
        updateTotalPrice();
        int newCount = app.getCartCount() - removed.count;
        app.setCartCount(Math.max(newCount, 0));
        Toast.makeText(this, removed.name + " 已移除", Toast.LENGTH_SHORT).show();
    }

    private void updateTotalPrice() {
        float total = 0;
        for (CartInfo item : cartList) {
            total += item.price * item.count;
        }
        tvTotalPrice.setText(String.format("总价：￥%.2f", total));
    }
}
