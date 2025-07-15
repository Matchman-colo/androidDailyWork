package com.example.e_7_5.model;

public class BillInfo {
    public int id;
    public String date;
    public String type; // 收入 或 支出
    public float amount;
    public String reason;

    public BillInfo() {}

    public BillInfo(String date, String type, float amount, String reason) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.reason = reason;
    }

    public BillInfo(int id, String date, String type, float amount, String reason) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.reason = reason;
    }
}
