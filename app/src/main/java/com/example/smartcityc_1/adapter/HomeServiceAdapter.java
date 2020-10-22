package com.example.smartcityc_1.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityc_1.R;
import com.example.smartcityc_1.bean.HomeService;
import com.example.smartcityc_1.bean.SerViceOrder;
import com.example.smartcityc_1.net.VolleyImage;
import com.example.smartcityc_1.net.VolleyLo;
import com.example.smartcityc_1.net.VolleyLoImage;
import com.example.smartcityc_1.net.VolleyTo;
import com.example.smartcityc_1.util.OnClickItem;
import com.example.smartcityc_1.util.Utils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/8 at 8:30
 */
public class HomeServiceAdapter extends ArrayAdapter<SerViceOrder> {

    public HomeServiceAdapter(@NonNull Context context, @NonNull List<SerViceOrder> objects) {
        super(context, 0, objects);
    }

    @Override
    public int getCount() {
        return 10;
    }

    private OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.service_item, parent, false);
            holder = new ViewHolder();
            holder.itemLayout = convertView.findViewById(R.id.item_layout);
            holder.itemPhoto = convertView.findViewById(R.id.item_photo);
            holder.itemName = convertView.findViewById(R.id.item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == 9) {
            holder.itemPhoto.setImageResource(R.mipmap.more_service);
            holder.itemName.setText("更多服务");
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem.onClick(position, "更多服务");
                }
            });

        }else {
            final SerViceOrder homeService = getItem(position);
            VolleyTo volleyTo = new VolleyTo();
            volleyTo.setURl("service_info")
                    .setJsonObject("serviceid", homeService.getId())
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            final HomeService service = new Gson().fromJson(jsonObject.optJSONArray(Utils.Row).optJSONObject(0).toString(), HomeService.class);
                            holder.itemName.setText(service.getServiceName());
                            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onClickItem.onClick(position, service.getServiceName());
                                }
                            });
                            VolleyImage volleyImage = new VolleyImage();
                            volleyImage.setUrl(service.getIcon())
                                    .setVolleyLoImage(new VolleyLoImage() {
                                        @Override
                                        public void onResponse(Bitmap bitmap) {
                                            holder.itemPhoto.setImageBitmap(bitmap);
                                        }

                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {

                                        }
                                    }).start();
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();

        }

        return convertView;
    }


    static class ViewHolder {

        private LinearLayout itemLayout;
        private ImageView itemPhoto;
        private TextView itemName;
    }

    private void initView() {
    }
}
