package com.example.a_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 数字和小数点按钮
    Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_pt;
    // 运算符按钮
    Button btn_mul, btn_div, btn_add, btn_sub;
    ImageButton btn_squ;
    // 功能按钮
    Button btn_clr, btn_del, btn_eq;
    EditText et_input;
    boolean clr_flag; // 判断是否清空输入框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化所有按钮
        initButtons();
        // 设置点击监听
        setClickListeners();
    }

    private void initButtons() {
        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_pt = findViewById(R.id.btn_pt);
        btn_add = findViewById(R.id.btn_add);
        btn_sub = findViewById(R.id.btn_sub);
        btn_mul = findViewById(R.id.btn_mul);
        btn_div = findViewById(R.id.btn_div);
        btn_squ=findViewById(R.id.btn_squ);
        btn_clr = findViewById(R.id.btn_clr);
        btn_del = findViewById(R.id.btn_del);
        btn_eq = findViewById(R.id.btn_eq);
        et_input = findViewById(R.id.et_input);
    }

    private void setClickListeners() {
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_pt.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
        btn_mul.setOnClickListener(this);
        btn_div.setOnClickListener(this);
        btn_squ.setOnClickListener(this);
        btn_clr.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_eq.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String str = et_input.getText().toString();
        int id = v.getId();

        // 数字和小数点处理
        if (isDigitOrDecimalButton(id)) {
            handleDigitInput(str, (Button) v);
        }
        // 运算符处理
        else if (isOperatorButton(id)) {
            handleOperatorInput(str, (Button) v);
        }
        // 开平方处理
        else if (id == R.id.btn_squ) {
            handleSquareRoot(str);
        }
        // 功能按钮处理
        else if (id == R.id.btn_clr) {
            et_input.setText("");
        } else if (id == R.id.btn_del) {
            handleDelete(str);
        } else if (id == R.id.btn_eq) {
            getResult();
        }
    }

    private boolean isDigitOrDecimalButton(int id) {
        return id == R.id.btn_0 || id == R.id.btn_1 || id == R.id.btn_2 ||
                id == R.id.btn_3 || id == R.id.btn_4 || id == R.id.btn_5 ||
                id == R.id.btn_6 || id == R.id.btn_7 || id == R.id.btn_8 ||
                id == R.id.btn_9 || id == R.id.btn_pt;
    }

    private boolean isOperatorButton(int id) {
        return id == R.id.btn_add || id == R.id.btn_sub ||
                id == R.id.btn_mul || id == R.id.btn_div;
    }

    private void handleDigitInput(String currentText, Button button) {
        if (clr_flag) {
            clr_flag = false;
            currentText = "";
        }
        et_input.setText(currentText + button.getText());
    }

    private void handleOperatorInput(String currentText, Button button) {
        if (clr_flag) {
            clr_flag = false;
            currentText = "";
        }

        // 如果已有运算符，则替换旧的
        if (currentText.contains("+") || currentText.contains("-") ||
                currentText.contains("×") || currentText.contains("÷")) {
            currentText = currentText.substring(0, currentText.indexOf(" "));
        }

        et_input.setText(currentText + " " + button.getText() + " ");
    }

    private void handleDelete(String currentText) {
        if (clr_flag) {
            clr_flag = false;
            currentText = "";
        } else if (currentText != null && !currentText.isEmpty()) {
            currentText = currentText.substring(0, currentText.length() - 1);
        }
        et_input.setText(currentText);
    }

    private void getResult() {
        String exp = et_input.getText().toString();
        if (exp == null || exp.isEmpty()) return;

        if (!exp.contains(" ")) return;
        if (clr_flag) {
            clr_flag = false;
            return;
        }

        clr_flag = true;

        String s1 = exp.substring(0, exp.indexOf(" "));
        String op = exp.substring(exp.indexOf(" ") + 1, exp.indexOf(" ") + 2);
        String s2 = exp.substring(exp.indexOf(" ") + 3);

        if (!s1.isEmpty() && !s2.isEmpty()) {
            double d1 = Double.parseDouble(s1);
            double d2 = Double.parseDouble(s2);
            double result = performCalculation(d1, d2, op);

            if (isIntegerResult(s1, s2, op)) {
                et_input.setText(String.valueOf((int) result));
            } else {
                et_input.setText(String.valueOf(result));
            }
        }

    }

    private double performCalculation(double d1, double d2, String op) {
        switch (op) {
            case "+": return d1 + d2;
            case "-": return d1 - d2;
            case "×": return d1 * d2;
            case "÷": return d2 == 0 ? 0 : d1 / d2;
            default: return 0;
        }
    }
    private void handleSquareRoot(String currentText) {
        if (clr_flag) {
            clr_flag = false;
            currentText = "";
        }
        if (currentText == null || currentText.isEmpty()) return;

        try {
            double num = Double.parseDouble(currentText);
            if (num < 0) {
                et_input.setText("错误");
            } else {
                double result = Math.sqrt(num);
                // 判断是否为整数
                if (result == (int) result) {
                    et_input.setText(String.valueOf((int) result));
                } else {
                    et_input.setText(String.valueOf(result));
                }
            }
        } catch (NumberFormatException e) {
            et_input.setText("错误");
        }
    }


    private boolean isIntegerResult(String s1, String s2, String op) {
        return !s1.contains(".") && !s2.contains(".") && !op.equals("÷");
    }
}