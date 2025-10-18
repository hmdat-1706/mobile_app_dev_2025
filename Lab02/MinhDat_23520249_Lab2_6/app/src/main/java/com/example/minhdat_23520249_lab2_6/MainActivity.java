package com.example.minhdat_23520249_lab2_6;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etId, etFullName;
    private CheckBox cbIsManager;
    private Button btnAdd;
    private RecyclerView rvEmployees;

    private ArrayList<Employee> employeeList;
    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Ánh xạ View từ layout
        etId = findViewById(R.id.et_id);
        etFullName = findViewById(R.id.et_fullName);
        cbIsManager = findViewById(R.id.cb_isManager);
        btnAdd = findViewById(R.id.btn_add);
        rvEmployees = findViewById(R.id.rv_employees);

        //Khởi tạo danh sách và Adapter
        employeeList = new ArrayList<>();
        adapter = new EmployeeAdapter(this, employeeList);

        //Thiết lập cho RecyclerView
        rvEmployees.setAdapter(adapter);
        rvEmployees.setLayoutManager(new LinearLayoutManager(this));

        //Xử lý sự kiện khi nhấn nút "Add"
        btnAdd.setOnClickListener(v -> addNewEmployee());
    }

    private void addNewEmployee() {
        String id = etId.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();

        //Kiểm tra dữ liệu đầu vào
        if (id.isEmpty() || fullName.isEmpty()) {
            Toast.makeText(this, "Please enter ID and Full Name", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isManager = cbIsManager.isChecked();

        //Tạo đối tượng Employee mới
        Employee newEmployee = new Employee(id, fullName, isManager);
        employeeList.add(newEmployee);

        //Thông báo cho Adapter biết có một item mới đã được thêm vào cuối danh sách
        adapter.notifyItemInserted(employeeList.size() - 1);

        //Tự động cuộn đến vị trí của nhân viên mới thêm
        rvEmployees.scrollToPosition(employeeList.size() - 1);

        //Xóa trắng các ô nhập liệu để chuẩn bị cho lần nhập tiếp theo
        etId.setText("");
        etFullName.setText("");
        cbIsManager.setChecked(false);
        etId.requestFocus();
    }
}
    