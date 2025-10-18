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

import java.util.List;

public class DishAdapter extends ArrayAdapter<Dish> {

    public DishAdapter(@NonNull Context context, @NonNull List<Dish> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_dish, parent, false);
        }

        Dish dish = getItem(position);

        ImageView ivDishImage = convertView.findViewById(R.id.iv_dish_image);
        TextView tvDishName = convertView.findViewById(R.id.tv_dish_name);
        ImageView ivPromotionStar = convertView.findViewById(R.id.iv_promotion_star);

        if (dish != null) {
            ivDishImage.setImageResource(dish.getThumbnail().getImg());
            tvDishName.setText(dish.getName());

            // Marquee (chữ chạy) nếu tên quá dài
            tvDishName.setSelected(true);

            if (dish.hasPromotion()) {
                ivPromotionStar.setVisibility(View.VISIBLE);
            } else {
                ivPromotionStar.setVisibility(View.GONE);
            }
        }
        return convertView;
    }
}
