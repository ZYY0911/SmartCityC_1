package com.example.smartcityc_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartcityc_1.R;
import com.example.smartcityc_1.bean.HomeService;
import com.example.smartcityc_1.bean.OrderTitle;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/8 at 10:45
 */
public class OrderAdapter extends ArrayAdapter<OrderTitle> {

    public OrderAdapter(@NonNull Context context, @NonNull List<OrderTitle> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_title_item, parent, false);
            holder = new ViewHolder();
            holder.itemDdh = convertView.findViewById(R.id.item_ddh);
            holder.itemDdlx = convertView.findViewById(R.id.item_ddlx);
            holder.itemRq = convertView.findViewById(R.id.item_rq);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderTitle orderTitle = getItem(position);
        holder.itemDdh.setText(orderTitle.getId() + "");
        holder.itemDdlx.setText(orderTitle.getType());
        holder.itemRq.setText(orderTitle.getDate().replace(".0", ""));
        return convertView;
    }

    static class ViewHolder {

        private TextView itemDdh;
        private TextView itemDdlx;
        private TextView itemRq;
    }

    private void initView() {
    }
}
