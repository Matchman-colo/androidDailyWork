package com.example.e_7_5;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_7_5.model.BillDataHolder;
import com.example.e_7_5.model.BillInfo;
import com.example.e_7_5.util.DateUtil;
import android.content.Intent;


import java.util.Calendar;

public class BillAddActivity extends AppCompatActivity {

    private Button btnSelectDate;
    private RadioGroup rgType;
    private EditText etAmount, etReason;
    private Button btnSave;

    private String selectedDate; // 格式 yyyy-MM-dd

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);

        btnSelectDate = findViewById(R.id.btn_select_date);
        rgType = findViewById(R.id.rg_type);
        etAmount = findViewById(R.id.et_amount);
        etReason = findViewById(R.id.et_reason);
        btnSave = findViewById(R.id.btn_save);

        selectedDate = DateUtil.getTodayDate();
        btnSelectDate.setText(selectedDate);

        btnSelectDate.setOnClickListener(v -> showDatePickerDialog());

        btnSave.setOnClickListener(v -> saveBill());
        Button btnViewBills = findViewById(R.id.btn_view_bills);
        btnViewBills.setOnClickListener(v -> {
            Intent intent = new Intent(this, BillPagerActivity.class);
            startActivity(intent);
        });

    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate = DateUtil.formatDate(year, month, dayOfMonth);
                    btnSelectDate.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void saveBill() {
        String amountStr = etAmount.getText().toString().trim();
        String reason = etReason.getText().toString().trim();
        if (amountStr.isEmpty() || reason.isEmpty()) {
            Toast.makeText(this, "请填写金额和事由", Toast.LENGTH_SHORT).show();
            return;
        }

        float amount = Float.parseFloat(amountStr);
        String type = (rgType.getCheckedRadioButtonId() == R.id.rb_income) ? "收入" : "支出";

        BillInfo bill = new BillInfo(selectedDate, type, amount, reason);
        BillDataHolder.billList.add(bill);

        Toast.makeText(this, "账单保存成功", Toast.LENGTH_SHORT).show();
    }
}
