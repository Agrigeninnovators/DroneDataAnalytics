package com.example.agis;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class GridAdapter extends BaseAdapter {
    private final Context context;
    private final String[] cropStatus;
    private final String[] colors;

    public GridAdapter(Context context, String[] cropStatus, String[] colors) {
        this.context = context;
        this.cropStatus = cropStatus;
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return cropStatus != null ? cropStatus.length : 0;
    }

    @Override
    public Object getItem(int position) {
        return cropStatus != null ? cropStatus[position] : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            holder = new ViewHolder();
            holder.cropTextView = convertView.findViewById(R.id.crop_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Set the background color only
        String color = colors != null && position < colors.length ? colors[position] : "#808080";
        holder.cropTextView.setBackgroundColor(Color.parseColor(color));

        // Optionally, hide the text if you don't want to display it initially
        holder.cropTextView.setText("--");  // Clear the text if necessary

        return convertView;
    }

    static class ViewHolder {
        TextView cropTextView;
    }
}
