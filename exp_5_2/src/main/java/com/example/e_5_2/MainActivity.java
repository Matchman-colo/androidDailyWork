package com.example.e_5_2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etAge, etHeight, etWeight;
    private CheckBox cbMarried;
    private PersonDBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 确保布局名一致

        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        etHeight = findViewById(R.id.et_height);
        etWeight = findViewById(R.id.et_weight);
//        cbMarried = findViewById(R.id.cb_married);

        dbHelper = new PersonDBHelper(this);
        db = dbHelper.getWritableDatabase();

        findViewById(R.id.btn_add).setOnClickListener(v -> addPerson());
        findViewById(R.id.btn_delete).setOnClickListener(v -> deletePerson());
        findViewById(R.id.btn_update).setOnClickListener(v -> updatePerson());
        findViewById(R.id.btn_query).setOnClickListener(v -> queryPerson());
    }

    private void addPerson() {
        String name = etName.getText().toString();
        int age = Integer.parseInt(etAge.getText().toString());
        float height = Float.parseFloat(etHeight.getText().toString());
        float weight = Float.parseFloat(etWeight.getText().toString());
        int married = 0;

        db.execSQL("INSERT INTO person(name, age, height, weight, married) VALUES (?, ?, ?, ?, ?)",
                new Object[]{name, age, height, weight, married});
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
    }

    private void deletePerson() {
        String name = etName.getText().toString();
        db.execSQL("DELETE FROM person WHERE name=?", new Object[]{name});
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
    }

    private void updatePerson() {
        String name = etName.getText().toString();
        int age = Integer.parseInt(etAge.getText().toString());
        float height = Float.parseFloat(etHeight.getText().toString());
        float weight = Float.parseFloat(etWeight.getText().toString());
        int married =  0;

        db.execSQL("UPDATE person SET age=?, height=?, weight=?, married=? WHERE name=?",
                new Object[]{age, height, weight, married, name});
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }

    private void queryPerson() {
        String name = etName.getText().toString();
        Cursor cursor = db.rawQuery("SELECT * FROM person WHERE name=?", new String[]{name});
        if (cursor.moveToFirst()) {
            etAge.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("age"))));
            etHeight.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow("height"))));
            etWeight.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow("weight"))));
            //cbMarried.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow("married")) == 1);
            Toast.makeText(this, "查询成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未找到记录", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}
