package com.example.minhdat_23520249_lab2_2; // Hãy chắc chắn đây là package của bạn

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //Khai báo các biến cho View và dữ liệu
    private EditText etNewName;
    private Button btnSubmit;
    private TextView tvSelection;
    private ListView lvPerson;
    private ArrayList<String> names;
    private ArrayAdapter<String> adapter;

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

        etNewName = findViewById(R.id.et_newname);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvSelection = findViewById(R.id.tv_selection);
        lvPerson = findViewById(R.id.lv_person);

        //Khởi tạo dữ liệu và Adapter
        names = new ArrayList<>(Arrays.asList("Teo", "Ty", "Bin", "Bo"));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

        lvPerson.setAdapter(adapter);//Gán Adapter cho ListView
        setupEventListeners();
    }

    private void setupEventListeners() {
        //Sự kiện khi nhấn nút Nhập
        btnSubmit.setOnClickListener(v -> {
            String newName = etNewName.getText().toString().trim();
            if (!newName.isEmpty()) {
                names.add(newName);
                adapter.notifyDataSetChanged(); //Cập nhật ListView
                etNewName.setText(""); //Xóa chữ trong EditText
                Toast.makeText(MainActivity.this, "Đã thêm " + newName , Toast.LENGTH_SHORT).show();//Thông báo đã thêm tên vào danh sách
            } else { //Thông báo khi nội dung ô nhập tróng
                Toast.makeText(MainActivity.this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            }
        });

        //Sự kiện khi click vào một mục trong ListView
        lvPerson.setOnItemClickListener((parent, view, position, id) -> {
            String selectedValue = names.get(position);
            tvSelection.setText("position: " + position + " ; value: " + selectedValue);
        });

        //Sự kiện khi long click vào một mục trong ListView (để xóa)
        lvPerson.setOnItemLongClickListener((parent, view, position, id) -> {
            Toast.makeText(MainActivity.this, "Đã xóa: " + names.get(position), Toast.LENGTH_SHORT).show();//Thông báo đã xóa tên
            names.remove(position);
            adapter.notifyDataSetChanged(); //Cập nhật ListView
            return true; //Báo hiệu sự kiện đã được xử lý
        });
    }
}
