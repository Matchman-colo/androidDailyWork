package com.example.e_5_3;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder> {

    private List<GoodsInfo> goodsList;
    private OnAddToCartClickListener listener;

    public interface OnAddToCartClickListener {
        void onAddClick(GoodsInfo goods);
    }

    public GoodsAdapter(List<GoodsInfo> goodsList, OnAddToCartClickListener listener) {
        this.goodsList = goodsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goods, parent, false);
        return new GoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsViewHolder holder, int position) {
        GoodsInfo goods = goodsList.get(position);
        holder.tvName.setText(goods.name);
        holder.tvDesc.setText(goods.description); // 设置描述
        holder.tvPrice.setText("￥" + goods.price);
        holder.ivGoodsPic.setImageResource(goods.picPath);
        holder.btnAdd.setOnClickListener(v -> listener.onAddClick(goods));

        // 点击图片跳转到商品详情页
        holder.ivGoodsPic.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ShoppingDetailActivity.class);
            intent.putExtra("goodsInfo", goods); // 传递商品信息
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    static class GoodsViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc, tvPrice;
        ImageView ivGoodsPic;
        Button btnAdd;

        public GoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvGoodsName);
            tvDesc = itemView.findViewById(R.id.tvGoodsDesc); // 初始化描述
            tvPrice = itemView.findViewById(R.id.tvGoodsPrice);
            ivGoodsPic = itemView.findViewById(R.id.ivGoodsPic);
            btnAdd = itemView.findViewById(R.id.btnAddCart);
        }
    }
}
