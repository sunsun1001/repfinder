package com.example.sunsun1001.repfinder;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;


/**
 * Created by sunsun1001 on 3/3/16.
 */
public class WearableAdapter extends WearableListView.Adapter {
    private List<String> mItems;
    private final LayoutInflater mInflater;

    /**
     * Constructor
     *
     * @param context
     * @param items
     */
    public WearableAdapter(Context context, String[] items) {
        mInflater = LayoutInflater.from(context);
        mItems = Arrays.asList(items);
    }

    /**
     * create a new view for a row in the list
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
    }

    /**
     * To determines the position (that is index) of every visible
     * row in the list
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        TextView textView = itemViewHolder.mItemTextView;
        textView.setText(mItems.get(position));
    }

    /**
     * To know how many items are in the list
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView mItemTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemTextView = (TextView) itemView.findViewById(R.id.name);
        }
    }
}