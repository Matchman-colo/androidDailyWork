package com.example.e_7_5;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.e_7_5.model.BillDataHolder;
import com.example.e_7_5.model.BillInfo;
import com.example.e_7_5.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class BillFragment extends Fragment {

    private int month;
    private ListView lvBill;
    private TextView tvSummary;
    private BillListAdapter adapter;
    private List<BillInfo> monthList = new ArrayList<>();

    public static BillFragment newInstance(int month) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putInt("month", month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        month = getArguments() != null ? getArguments().getInt("month") : 1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        lvBill = view.findViewById(R.id.lv_bill);
        tvSummary = view.findViewById(R.id.tv_summary);

        adapter = new BillListAdapter(getActivity(), monthList);
        lvBill.setAdapter(adapter);

        // 删除账单
        lvBill.setOnItemLongClickListener((parent, view1, position, id) -> {
            BillInfo bill = monthList.get(position);
            new AlertDialog.Builder(getActivity())
                    .setTitle("确认删除")
                    .setMessage("确定删除该账单？")
                    .setPositiveButton("删除", (dialog, which) -> {
                        BillDataHolder.billList.remove(bill);
                        refreshList(); // 刷新
                    })
                    .setNegativeButton("取消", null)
                    .show();
            return true;
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        monthList.clear();
        float income = 0f, expense = 0f;
        for (BillInfo bill : BillDataHolder.billList) {
            if (getMonthFromDate(bill.date) == month) {
                monthList.add(bill);
                if ("收入".equals(bill.type)) {
                    income += bill.amount;
                } else {
                    expense += bill.amount;
                }
            }
        }
        adapter.notifyDataSetChanged();
        tvSummary.setText(String.format("收入：%.2f   支出：%.2f", income, expense));
    }

    private int getMonthFromDate(String date) {
        try {
            return Integer.parseInt(date.substring(5, 7));
        } catch (Exception e) {
            return 1;
        }
    }
}
