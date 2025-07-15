package com.example.e_5_3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingchannelActivity extends AppCompatActivity {

    private RecyclerView rvGoods;
    private TextView tvCartCount;

    private List<GoodsInfo> goodsList = new ArrayList<>();
    private GoodsAdapter adapter;

    private MainApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_channel);

        rvGoods = findViewById(R.id.rvGoods);
        tvCartCount = findViewById(R.id.tvCartCount);

        app = (MainApplication) getApplication();

        // 模拟数据
        goodsList.add(new GoodsInfo(1, "机械革命", "旷世X", 7499, R.drawable.jxgm));
        goodsList.add(new GoodsInfo(2, "联想小新", "14 Pro", 5999, R.drawable.lxxx));
        goodsList.add(new GoodsInfo(3, "七彩虹", "隐星P16", 6664, R.drawable.qch));
        // TODO: 可以从数据库加载真实数据

        adapter = new GoodsAdapter(goodsList, this::onAddToCartClicked);
        rvGoods.setLayoutManager(new GridLayoutManager(this, 2));
        rvGoods.setAdapter(adapter);

        updateCartCount();

        tvCartCount.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingchannelActivity.this, ShoppingCartActivity.class);
            startActivity(intent);
        });
    }

    private void onAddToCartClicked(GoodsInfo goods) {
        app.addToCart(goods.id, goods.name, goods.price,goods.picPath,goods.description);
        // 现在是真正添加到购物车
        updateCartCount();
        Toast.makeText(this, goods.name + " 已加入购物车", Toast.LENGTH_SHORT).show();
    }


    private void updateCartCount() {
        tvCartCount.setText("购物车: " + app.getCartCount());
    }
}
