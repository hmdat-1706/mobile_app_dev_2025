package com.example.minhdat_23520249_lab2_5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etDishName;
    private Spinner spThumbnail;
    private CheckBox cbPromotion;
    private Button btnAddDish;
    private GridView gvDishes;

    private ArrayList<Dish> dishList;
    private DishAdapter dishAdapter;
    private ThumbnailAdapter thumbnailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ View
        etDishName = findViewById(R.id.et_dish_name);
        spThumbnail = findViewById(R.id.sp_thumbnail);
        cbPromotion = findViewById(R.id.cb_promotion);
        btnAddDish = findViewById(R.id.btn_add_dish);
        gvDishes = findViewById(R.id.gv_dishes);

        // Thiết lập Spinner
        thumbnailAdapter = new ThumbnailAdapter(this, Thumbnail.values());
        spThumbnail.setAdapter(thumbnailAdapter);

        // Thiết lập GridView
        dishList = new ArrayList<>();
        dishAdapter = new DishAdapter(this, dishList);
        gvDishes.setAdapter(dishAdapter);

        // Xử lý sự kiện click cho nút "Add"
        btnAddDish.setOnClickListener(v -> addNewDish());
    }

    private void addNewDish() {
        String name = etDishName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter dish name", Toast.LENGTH_SHORT).show();
            return;
        }

        Thumbnail selectedThumbnail = (Thumbnail) spThumbnail.getSelectedItem();
        boolean hasPromotion = cbPromotion.isChecked();

        // Tạo đối tượng Dish mới và thêm vào danh sách
        Dish newDish = new Dish(name, selectedThumbnail, hasPromotion);
        dishList.add(newDish);

        // Cập nhật GridView
        dishAdapter.notifyDataSetChanged();

        // Reset các trường nhập liệu
        resetInputFields();

        // Hiển thị thông báo
        Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
    }

    private void resetInputFields() {
        etDishName.setText("");
        spThumbnail.setSelection(0); // Quay về thumbnail đầu tiên
        cbPromotion.setChecked(false);
        etDishName.requestFocus();
    }
}
