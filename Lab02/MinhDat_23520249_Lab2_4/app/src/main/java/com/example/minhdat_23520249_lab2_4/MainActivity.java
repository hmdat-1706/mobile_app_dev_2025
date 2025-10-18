package com.example.minhdat_23520249_lab2_4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast; // Thêm Toast để thông báo
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etId, etFullName;
    private CheckBox cbIsManager;
    private Button btnAdd;
    private ListView lvEmployees;

    private ArrayList<Employee> employees;
    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Xử lý để nội dung không bị ActionBar che
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các biến với các View trong file XML bằng ID
        etId = findViewById(R.id.et_id);
        etFullName = findViewById(R.id.et_fullName);
        cbIsManager = findViewById(R.id.cb_isManager);
        btnAdd = findViewById(R.id.btn_add);
        lvEmployees = findViewById(R.id.lv_employees);

        // 1. Khởi tạo danh sách
        employees = new ArrayList<>();

        // 2. Tạo Adapter với danh sách đã có dữ liệu
        adapter = new EmployeeAdapter(this, R.layout.item_employee, employees);

        // 3. Gán adapter cho ListView để hiển thị dữ liệu
        lvEmployees.setAdapter(adapter);

        // Thiết lập sự kiện lắng nghe cho nút "Add"
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEmployee();
            }
        });
    }

    private void addNewEmployee() {
        String id = etId.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();

        if (id.isEmpty() || fullName.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ ID và Tên", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isManager = cbIsManager.isChecked();

        // BỎ COMMENT CÁC DÒNG NÀY ĐỂ THÊM NHÂN VIÊN
        Employee employee = new Employee(id, fullName, isManager);
        employees.add(employee);
        adapter.notifyDataSetChanged(); // Cập nhật lại giao diện ListView

        // Xóa dữ liệu cũ và focus lại ô ID
        etId.setText("");
        etFullName.setText("");
        cbIsManager.setChecked(false);
        etId.requestFocus();
    }
}
