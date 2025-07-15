package com.example.e_7_5;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class BillPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private String[] monthTitles = {
            "1月", "2月", "3月", "4月", "5月", "6月",
            "7月", "8月", "9月", "10月", "11月", "12月"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_pager);

        viewPager = findViewById(R.id.view_pager);
        BillPagerAdapter adapter = new BillPagerAdapter(getSupportFragmentManager(), monthTitles);
        viewPager.setAdapter(adapter);
    }

    // 添加菜单（右上角添加按钮）
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    // 点击菜单按钮：跳转到添加页面
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            Intent intent = new Intent(this, BillAddActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

