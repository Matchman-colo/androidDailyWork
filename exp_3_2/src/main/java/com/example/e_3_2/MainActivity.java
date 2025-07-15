package com.example.e_3_2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etRegion, etStreet, etNumber, etName, etPhone;
    private Button btnSubmit;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etRegion = findViewById(R.id.et_region);
        etStreet = findViewById(R.id.et_street);
        etNumber = findViewById(R.id.et_number);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        btnSubmit = findViewById(R.id.btn_submit);
        tvResult = findViewById(R.id.tv_result);

        btnSubmit.setOnClickListener(v -> {
            String region = etRegion.getText().toString().trim();
            String street = etStreet.getText().toString().trim();
            String number = etNumber.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (region.isEmpty() || street.isEmpty() || number.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                tvResult.setText("请填写完整的收货信息！");
            } else {
                String result = "地址：" + region + " " + street + " " + number + "\n"
                        + "收货人：" + name + "\n"
                        + "电话：" + phone;
                tvResult.setText(result);
            }
        });
    }
}