package com.ban.banlot.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ban.banlot.R;

import java.util.List;

/**
 * Created by Ban on 2016/7/26.
 */
public class LotAdapter extends CommonBaseAdapter<String> {
    public LotAdapter(Context context, List<String> datas) {
        super(context, datas, R.layout.item_lot);
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {
        String value = datas.get(pos);
        CommonViewHolder holder = CommonViewHolder.getInstance(context, convertView, parent, resourceId);
        TextView textView = holder.getView(R.id.item);
        textView.setText(value);

        return holder.getConvertView();
    }
}