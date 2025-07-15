package com.example.e_5_3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartInfo> cartList;
    private OnRemoveClickListener listener;

    public interface OnRemoveClickListener {
        void onRemove(int position);
    }

    public CartAdapter(List<CartInfo> cartList, OnRemoveClickListener listener) {
        this.cartList = cartList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartInfo item = cartList.get(position);
        holder.tvName.setText(item.name);
        holder.tvDesc.setText(item.desc); // 展示描述
        holder.tvPrice.setText("￥" + item.price);
        holder.tvCount.setText("数量：" + item.count);
        holder.btnRemove.setOnClickListener(v -> listener.onRemove(position));

        if (item.picPath != 0) {
            holder.ivCartImage.setImageResource(item.picPath);
        } else {
//            holder.ivCartImage.setImageResource(R.drawable.default_image);
        }
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc, tvPrice, tvCount;
        Button btnRemove;
        ImageView ivCartImage;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCartName);
            tvDesc = itemView.findViewById(R.id.tvCartDesc); // 新增
            tvPrice = itemView.findViewById(R.id.tvCartPrice);
            tvCount = itemView.findViewById(R.id.tvCartCount);
            btnRemove = itemView.findViewById(R.id.btnRemoveItem);
            ivCartImage = itemView.findViewById(R.id.ivCartImage);
        }
    }
}
