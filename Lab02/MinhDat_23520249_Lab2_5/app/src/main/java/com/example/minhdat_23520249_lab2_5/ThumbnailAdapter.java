package com.example.minhdat_23520249_lab2_5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ThumbnailAdapter extends ArrayAdapter<Thumbnail> {

    public ThumbnailAdapter(@NonNull Context context, @NonNull Thumbnail[] objects) {
        super(context, 0, objects);
    }

    //Hiển thị cho item được chọn trên Spinner
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_selected_thumbnail, parent, false);
        }

        Thumbnail thumbnail = getItem(position);
        TextView tvSelectedName = convertView.findViewById(R.id.tv_selected_thumbnail_name);
        if (thumbnail != null) {
            tvSelectedName.setText(thumbnail.getName());
        }

        return convertView;
    }

    //Hiển thị cho danh sách dropdown (dạng dialog)
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_thumbnail, parent, false);
        }

        Thumbnail thumbnail = getItem(position);
        ImageView ivThumbnail = convertView.findViewById(R.id.iv_thumbnail_image);
        TextView tvThumbnailName = convertView.findViewById(R.id.tv_thumbnail_name);
        if (thumbnail != null) {
            ivThumbnail.setImageResource(thumbnail.getImg());
            tvThumbnailName.setText(thumbnail.getName());
        }
        return convertView;
    }
}
