package com.example.e_5_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CartDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "shopping.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_GOODS = "goods_info";
    public static final String TABLE_CART = "cart_info";

    public CartDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlGoods = "CREATE TABLE IF NOT EXISTS " + TABLE_GOODS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "description TEXT," +
                "price REAL NOT NULL," +
                "pic_path INTEGER" +    // 图片资源ID
                ");";
        db.execSQL(sqlGoods);

        String sqlCart = "CREATE TABLE IF NOT EXISTS " + TABLE_CART + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "goods_id INTEGER NOT NULL," +
                "name TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "count INTEGER NOT NULL," +
                "pic_path INTEGER" +    // 图片资源ID，新增字段
                ");";
        db.execSQL(sqlCart);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    public CartInfo getCartInfoByGoodsId(int goodsId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_CART, null, "goods_id=?",
                new String[]{String.valueOf(goodsId)}, null, null, null);

        CartInfo cartInfo = null;
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
            int count = cursor.getInt(cursor.getColumnIndexOrThrow("count"));
            int picPath = cursor.getInt(cursor.getColumnIndexOrThrow("pic_path"));

            cartInfo = new CartInfo(id, goodsId, name, price, count, picPath);
            cursor.close();
        }
        return cartInfo;
    }

    public long insertCartInfo(CartInfo cartInfo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("goods_id", cartInfo.goodsId);
        values.put("name", cartInfo.name);
        values.put("price", cartInfo.price);
        values.put("count", cartInfo.count);
        values.put("pic_path", cartInfo.picPath);
        return db.insert(TABLE_CART, null, values);
    }

    public int updateCartInfo(CartInfo cartInfo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("count", cartInfo.count);
        values.put("pic_path", cartInfo.picPath);
        return db.update(TABLE_CART, values, "id=?", new String[]{String.valueOf(cartInfo.id)});
    }

    public int deleteCartInfoById(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_CART, "id=?", new String[]{String.valueOf(id)});
    }

    public Cursor getAllCartInfo() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_CART, null, null, null, null, null, null);
    }

    public int getTotalCount() {
        int totalCount = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(count) FROM " + TABLE_CART, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                totalCount = cursor.getInt(0);
            }
            cursor.close();
        }
        return totalCount;
    }

    public float getTotalPrice() {
        float totalPrice = 0f;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(price * count) FROM " + TABLE_CART, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                totalPrice = cursor.getFloat(0);
            }
            cursor.close();
        }
        return totalPrice;
    }

    public int clearCart() {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_CART, null, null);
    }
}
