package com.example.minhdat_23520249_lab2_1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        final String[] arr = {"Teo", "Ty", "Bin", "Bo"}; //Khởi tạo mảng chứa dữ liệu
        final TextView tvSeletion = findViewById(R.id.tv_selection);
        ListView lvPerson = findViewById(R.id.lv_person);

        ArrayAdapter<String> adapter = new ArrayAdapter<String> //Tạo và gán Adapter cho ListView
                (this, android.R.layout.simple_list_item_1, arr);
        lvPerson.setAdapter(adapter);

        lvPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() { //Thiết lập sự kiện click cho item trong ListView
            @Override
            public void onItemClick(AdapterView<?>arg0, View arg1, int arg2, long arg3) {
                tvSeletion.setText("position: " + arg2 + " ; " + "value= " + arr[arg2]);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}