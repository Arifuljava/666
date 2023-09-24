package com.grozziie.adminsection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomSpinnerAdapter extends BaseAdapter {
    private Context context;
    private String[] names;
    private int[] icons;

    public CustomSpinnerAdapter(Context context, String[] names, int[] icons) {
        this.context = context;
        this.names = names;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item_layout, parent, false);
        }

        ImageView iconImageView = convertView.findViewById(R.id.your_icon_image_view_id);
        TextView nameTextView = convertView.findViewById(R.id.your_name_text_view_id);

        iconImageView.setImageResource(icons[position]);
        nameTextView.setText(names[position]);

        return convertView;
    }
}