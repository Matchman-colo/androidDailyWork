package com.example.e_5_3;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ShoppingDetailActivity extends AppCompatActivity {

    private ImageView ivGoodsPic;
    private TextView tvGoodsName, tvGoodsPrice, tvGoodsDescription, tvCartCount;
    private Button btnAddCart;

    private GoodsInfo goodsInfo;
    private MainApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);

        ivGoodsPic = findViewById(R.id.ivGoodsPic);
        tvGoodsName = findViewById(R.id.tvGoodsName);
        tvGoodsPrice = findViewById(R.id.tvGoodsPrice);
        tvGoodsDescription = findViewById(R.id.tvGoodsDescription);
        btnAddCart = findViewById(R.id.btnAddCart);
        tvCartCount = findViewById(R.id.tvCartCount);

        app = (MainApplication) getApplication();

        // 从Intent中接收GoodsInfo对象（需要保证传入时Serializable传递）
        Intent intent = getIntent();
        goodsInfo = (GoodsInfo) intent.getSerializableExtra("goodsInfo");

        if (goodsInfo == null) {
            Toast.makeText(this, "商品数据错误", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 显示商品信息
        tvGoodsName.setText(goodsInfo.name);
        tvGoodsPrice.setText(String.format("价格：￥%.2f", goodsInfo.price));
        tvGoodsDescription.setText(goodsInfo.description);

        // 使用 Picasso 加载图片
        Picasso.get().load(goodsInfo.picPath).into(ivGoodsPic);
        updateCartCount();

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 调用数据库操作，增加或更新购物车里的商品
                boolean success = addOrUpdateCart(goodsInfo);

                if (success) {
                    Toast.makeText(ShoppingDetailActivity.this, "已加入购物车", Toast.LENGTH_SHORT).show();
                    updateCartCount();
                } else {
                    Toast.makeText(ShoppingDetailActivity.this, "添加购物车失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateCartCount() {
        int count = app.getCartCount();
        tvCartCount.setText("购物车数量：" + count);
    }

    private boolean addOrUpdateCart(GoodsInfo goods) {
        try {
            CartDBHelper dbHelper = new CartDBHelper(this);

            // 查询购物车里是否存在该商品（用 goodsId）
            CartInfo cartInfo = dbHelper.getCartInfoByGoodsId(goods.id);

            if (cartInfo == null) {
                // 新增购物车项，数量为1
                CartInfo newCart = new CartInfo(0, goods.id, goods.name, goods.price, 1,goods.picPath);
                dbHelper.insertCartInfo(newCart);
            } else {
                // 商品已存在，数量+1
                cartInfo.count += 1;
                dbHelper.updateCartInfo(cartInfo);
            }

            // 更新全局购物车数据
            updateGlobalCartList();
            // 更新全局购物车数量
            int totalCount = dbHelper.getTotalCount();
            app.setCartCount(totalCount);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void updateGlobalCartList() {
        // 从数据库中获取最新的购物车数据
        CartDBHelper dbHelper = new CartDBHelper(this);
        Cursor cursor = dbHelper.getAllCartInfo();
        List<CartInfo> newCartList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int goodsId = cursor.getInt(cursor.getColumnIndexOrThrow("goods_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                int count = cursor.getInt(cursor.getColumnIndexOrThrow("count"));
                int picPath = cursor.getInt(cursor.getColumnIndexOrThrow("pic_path"));

                CartInfo cartInfo = new CartInfo(id, goodsId, name, price, count, picPath);
                newCartList.add(cartInfo);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // 更新全局购物车列表
        app.getCartList().clear();
        app.getCartList().addAll(newCartList);
    }
}
