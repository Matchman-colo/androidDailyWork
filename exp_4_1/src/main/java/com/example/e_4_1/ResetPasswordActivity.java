package com.example.e_4_1;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPasswordActivity extends Activity {

    private EditText etNewPassword;
    private EditText etConfirmPassword;
    private EditText etResetCode;
    private Button btnGetResetCode;
    private Button btnConfirmReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // 绑定控件
        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        etResetCode = findViewById(R.id.et_reset_code);
        btnGetResetCode = findViewById(R.id.btn_get_reset_code);
        btnConfirmReset = findViewById(R.id.btn_confirm_reset);

        // 获取验证码按钮点击事件
        btnGetResetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这里应该接入验证码发送逻辑
                Toast.makeText(ResetPasswordActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
            }
        });

        // 确认重置按钮点击事件
        btnConfirmReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = etNewPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                String code = etResetCode.getText().toString().trim();

                if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(code)) {
                    Toast.makeText(ResetPasswordActivity.this, "请填写所有信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 模拟密码重置成功
                Toast.makeText(ResetPasswordActivity.this, "密码重置成功", Toast.LENGTH_SHORT).show();
                // 这里可以跳转回登录界面或关闭当前页面
                finish();
            }
        });
    }
}
