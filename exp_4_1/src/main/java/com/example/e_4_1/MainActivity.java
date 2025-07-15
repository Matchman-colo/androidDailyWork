package com.example.e_4_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioButton rbPasswordLogin, rbCodeLogin;
    private EditText etPhone, etPassword;
    private LinearLayout layoutCode;
    private TextView tvForgot;
    private CheckBox cbRemember;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化控件
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        layoutCode = findViewById(R.id.layout_code);
        tvForgot = findViewById(R.id.tv_forgot);
        cbRemember = findViewById(R.id.cb_remember);
        Button btnLogin = findViewById(R.id.btn_login);

        // 初始化共享参数
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // 自动填充已记住的账号信息
        boolean isRemembered = sharedPreferences.getBoolean("remember", false);
        if (isRemembered) {
            String phone = sharedPreferences.getString("phone", "");
            String password = sharedPreferences.getString("password", "");
            etPhone.setText(phone);
            etPassword.setText(password);
            cbRemember.setChecked(true);
        }

        etPassword.setVisibility(View.VISIBLE);

        // 忘记密码跳转
        tvForgot.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });

        // 登录按钮点击事件
        btnLogin.setOnClickListener(v -> {
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (cbRemember.isChecked()) {
                editor.putString("phone", phone);
                editor.putString("password", password);
                editor.putBoolean("remember", true);
            } else {
                editor.clear();
            }
            editor.apply();

            Toast.makeText(MainActivity.this, "执行登录逻辑", Toast.LENGTH_SHORT).show();
        });
    }
}
