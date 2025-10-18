package com.example.minhdat_23520249_lab2_4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

// Import lớp ComponentActivity để tương thích với Theme Material 3
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

// Kế thừa từ ComponentActivity thay vì AppCompatActivity
public class MainActivity extends AppCompatActivity {

    // Khai báo các biến cho thành phần giao diện (UI Components)
    private EditText etId, etFullName;
    private CheckBox cbIsManager;
    private Button btnAdd;
    private ListView lvEmployees;

    // Khai báo các biến cho dữ liệu và Adapter
    private ArrayList<Employee> employees;
    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các biến với các View trong file XML bằng ID
        etId = findViewById(R.id.et_id);
        etFullName = findViewById(R.id.et_fullName);
        cbIsManager = findViewById(R.id.cb_isManager);
        btnAdd = findViewById(R.id.btn_add);
        lvEmployees = findViewById(R.id.lv_employees);

        // 1. Khởi tạo danh sách và thêm dữ liệu mẫu (nếu có)
        employees = new ArrayList<>();
        // Bạn có thể thêm dữ liệu mẫu tại đây để kiểm tra
        // employees.add(new Employee("1", "Nguyễn Văn An", false));
        // employees.add(new Employee("2", "Trần Thị Bích", true));

        // 2. Tạo Adapter với danh sách đã có dữ liệu
        adapter = new EmployeeAdapter(this, R.layout.item_employee, employees);

        // 3. Gán adapter cho ListView để hiển thị dữ liệu
        lvEmployees.setAdapter(adapter);

        // Thiết lập sự kiện lắng nghe cho nút "Add"
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức xử lý logic thêm nhân viên
                addNewEmployee();
            }
        });
    }

    /**
     * Phương thức này xử lý logic thêm một nhân viên mới vào danh sách.
     */
    private void addNewEmployee() {
        // Lấy dữ liệu người dùng nhập từ các ô EditText
        String id = etId.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();

        // Kiểm tra nếu người dùng chưa nhập ID hoặc tên thì không thêm
        if (id.isEmpty() || fullName.isEmpty()) {
            return; // Dừng lại, không làm gì cả
        }

        // Lấy trạng thái của CheckBox (được chọn hay không)
        boolean isManager = cbIsManager.isChecked();

        // Tạo một đối tượng Employee mới từ dữ liệu đã lấy
        Employee employee = new Employee(id, fullName, isManager);
        // Thêm đối tượng nhân viên mới vào danh sách
        employees.add(employee);

        // Thông báo cho adapter rằng dữ liệu đã thay đổi.
        // Adapter sẽ tự động cập nhật lại ListView.
        adapter.notifyDataSetChanged();

        // Xóa trống các ô nhập liệu để chuẩn bị cho lần nhập tiếp theo
        etId.setText("");
        etFullName.setText("");
        cbIsManager.setChecked(false);

        // Di chuyển con trỏ (focus) trở lại ô nhập ID để người dùng tiện nhập liệu
        etId.requestFocus();
    }
}
