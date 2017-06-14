package com.ban.banlot;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 通用ViewHolder
 * 
 * @author Ban
 * 
 */
public class CommonViewHolder {
	private View convertView;// 布局承载
	private SparseArray<View> views;// SparseArray 相当于优化版的HashMap
	public boolean SETED = false;

	public CommonViewHolder(Context context, ViewGroup parent, int resource) {
		this.views = new SparseArray<View>();
		this.convertView = LayoutInflater.from(context).inflate(resource,
				parent, false);
		this.convertView.setTag(this);
	}

	/**
	 * CommonViewHolder的实例化
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param resource
	 * @return CommonViewHolder
	 */
	public static CommonViewHolder getInstance(Context context,
			View convertView, ViewGroup parent, int resource) {
		if (convertView == null) {
			return new CommonViewHolder(context, parent, resource);
		} else {
			CommonViewHolder commonViewHolder = (CommonViewHolder) convertView
					.getTag();
			commonViewHolder.SETED = true;
			return commonViewHolder;
		}
	}

	public View getConvertView() {
		return convertView;
	}

	/**
	 * viewholder.textView = convertView.findViewById(id);
	 */
	public <T extends View> T getView(int id) {
		View view = views.get(id);
		if (view == null) {
			view = convertView.findViewById(id);
			views.put(id, view);
		}
		return (T) view;
	}
}
