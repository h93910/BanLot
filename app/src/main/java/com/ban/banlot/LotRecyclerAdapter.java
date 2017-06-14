package com.ban.banlot;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ban on 2016/7/26.
 */
public class LotRecyclerAdapter extends CommonBaseRecyclerAdapter<String> {
    public LotRecyclerAdapter(Context context, List<String> datas) {
        super(context, datas, R.layout.item_lot);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolderAbs(ViewGroup parent) {
        return new ViewHold(LayoutInflater.from(context).inflate(resourceId, parent, false));
    }

    @Override
    public void onBindViewHolderAbs(RecyclerView.ViewHolder holder, int position) {
        ViewHold hold = (ViewHold) holder;
        hold.name.setText(datas.get(position));
    }

    class ViewHold extends CommonRecyclerViewHolder {
        private TextView name;

        public ViewHold(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item);
        }
    }
}