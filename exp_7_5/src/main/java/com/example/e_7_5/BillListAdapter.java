package com.example.e_7_5;

import android.content.Context;
import android.view.*;
import android.widget.*;
import com.example.e_7_5.model.BillInfo;

import java.util.List;

public class BillListAdapter extends BaseAdapter {

    private Context context;
    private List<BillInfo> billList;

    public BillListAdapter(Context context, List<BillInfo> billList) {
        this.context = context;
        this.billList = billList;
    }

    @Override
    public int getCount() {
        return billList.size();
    }

    @Override
    public Object getItem(int position) {
        return billList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BillInfo bill = billList.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);

        TextView tvDate = view.findViewById(R.id.tv_date);
        TextView tvReason = view.findViewById(R.id.tv_reason);
        TextView tvAmount = view.findViewById(R.id.tv_amount);

        tvDate.setText(bill.date);
        tvReason.setText(bill.reason);
        tvAmount.setText(String.format("%.2f", bill.amount));
        return view;
    }
}
