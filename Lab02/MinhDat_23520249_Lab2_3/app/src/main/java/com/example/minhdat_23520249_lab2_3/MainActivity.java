package com.example.minhdat_23520249_lab2_3; // Nhớ kiểm tra lại tên package

import android.os.Bundle;
import android.view.View; // THÊM IMPORT NÀY
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge; // THÊM IMPORT NÀY
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets; // THÊM IMPORT NÀY
import androidx.core.view.ViewCompat; // THÊM IMPORT NÀY
import androidx.core.view.WindowInsetsCompat; // THÊM IMPORT NÀY

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Khai báo các biến View với tên giống hệt ID trong file XML
    private EditText et_ID;
    private EditText et_name;
    private RadioGroup rg_Type;
    private Button bt_Nhap;
    private ListView lv_NhanVien;

    // Khai báo danh sách để lưu nhân viên và Adapter
    private ArrayList<Employee> employeeList;
    private ArrayAdapter<Employee> adapter;

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

        // Ánh xạ các biến với ID tương ứng trong file XML
        et_ID = findViewById(R.id.et_ID);
        et_name = findViewById(R.id.et_name);
        rg_Type = findViewById(R.id.rg_Type);
        bt_Nhap = findViewById(R.id.bt_Nhap);
        lv_NhanVien = findViewById(R.id.lv_NhanVien);

        // Khởi tạo danh sách và Adapter
        employeeList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeeList);

        // Gán Adapter cho ListView
        lv_NhanVien.setAdapter(adapter);

        // Thiết lập sự kiện click cho nút "Nhập"
        bt_Nhap.setOnClickListener(view -> { // Đổi tên biến 'v' thành 'view' cho rõ nghĩa
            addNewEmployee();
        });
    }

    public void addNewEmployee() {
        int radId = rg_Type.getCheckedRadioButtonId();
        String id = et_ID.getText().toString();
        String name = et_name.getText().toString();

        if (id.trim().isEmpty() || name.trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ Mã và Tên NV", Toast.LENGTH_SHORT).show();
            return;
        }

        Employee employee;

        if (radId == R.id.rb_CT) {
            employee = new EmployeeFullTime();
        } else {
            employee = new EmployeePartTime();
        }

        employee.setId(id);
        employee.setName(name);
        employeeList.add(employee);
        adapter.notifyDataSetChanged();

        et_ID.setText("");
        et_name.setText("");
        et_ID.requestFocus();
    }

    public abstract class Employee {
        protected String id;
        protected String name;
        public Employee() {}
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public abstract double tinhLuong();
        @Override
        public String toString() {
            return this.id + " - " + this.name;
        }
    }
    public class EmployeeFullTime extends Employee {
        @Override
        public double tinhLuong() { return 500.0; }
        @Override
        public String toString() { return super.toString() + " --> FullTime=" + tinhLuong(); }
    }
    public class EmployeePartTime extends Employee {
        @Override
        public double tinhLuong() { return 150.0; }
        @Override
        public String toString() { return super.toString() + " --> PartTime=" + tinhLuong(); }
    }
}
