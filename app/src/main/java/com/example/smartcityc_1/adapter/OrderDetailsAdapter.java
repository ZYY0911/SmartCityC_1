package com.example.smartcityc_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartcityc_1.R;
import com.example.smartcityc_1.bean.OrderDetails;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/8 at 10:53
 */
public class OrderDetailsAdapter extends ArrayAdapter<OrderDetails> {

    public OrderDetailsAdapter(@NonNull Context context, @NonNull List<OrderDetails> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_details_item, parent, false);
            holder = new ViewHolder();
            holder.itemName = convertView.findViewById(R.id.item_name);
            holder.itemSp = convertView.findViewById(R.id.item_sp);
            holder.itemDj = convertView.findViewById(R.id.item_dj);
            holder.itemSl = convertView.findViewById(R.id.item_sl);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderDetails orderDetails = getItem(position);
        holder.itemName.setText(orderDetails.getBusiness());
        holder.itemSp.setText(orderDetails.getCommodity());
        holder.itemDj.setText(orderDetails.getPrice() + "");
        holder.itemSl.setText(orderDetails.getCount());
        return convertView;
    }

    static class ViewHolder {

        private TextView itemName;
        private TextView itemSp;
        private TextView itemDj;
        private TextView itemSl;
    }

    private void initView() {
    }
}
