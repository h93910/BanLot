package com.ban.banlot;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.action;
import static android.R.attr.text;

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