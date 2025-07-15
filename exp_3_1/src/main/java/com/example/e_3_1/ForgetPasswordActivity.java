package com.example.e_3_1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText etRecoveryInfo;
    private Button btnSubmit;
    private Button btnBack; // 新增返回按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        etRecoveryInfo = findViewById(R.id.et_recovery_info);
        btnSubmit = findViewById(R.id.btn_submit);
        btnBack = findViewById(R.id.btn_back); // 绑定返回按钮

        btnSubmit.setOnClickListener(v -> {
            String info = etRecoveryInfo.getText().toString();
            if (info.isEmpty()) {
                Toast.makeText(this, "请输入手机号或邮箱", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "找回信息已提交", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> {
            finish(); // 直接关闭当前Activity，返回到上一个页面
        });
    }
}
