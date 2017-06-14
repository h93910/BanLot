package com.ban.banlot;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 通用的ListView适配器
 *
 * @param <T>
 * @author Ban
 */
public abstract class CommonBaseAdapter<T> extends BaseAdapter {
    protected List<T> datas;
    protected Context context;
    protected int resourceId;
    private boolean loadComplete = false;//加载信息完成

    public CommonBaseAdapter(Context context, List<T> datas, int resourceId) {
        this.datas = datas;
        this.context = context;
        this.resourceId = resourceId;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                loadComplete = true;
            }
        }, 10000);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public abstract View getView(int position, View convertView,
                                 ViewGroup parent);

    public interface OnLastListener {
        void doQueryNext();
    }

    public boolean isLoadComplete() {
        return loadComplete;
    }
}
