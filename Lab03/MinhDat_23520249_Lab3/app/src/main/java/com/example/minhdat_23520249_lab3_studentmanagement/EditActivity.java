package com.example.minhdat_23520249_lab3_studentmanagement;import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    private EditText etStudentId, etName, etEmail, etClassName;
    private Button btnSave, btnCancel;
    //Biến để lưu thông tin sinh viên nếu đang ở chế độ Sửa, null nếu là thêm mới
    private Student studentToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //Ánh xạ các view từ file layout XML vào biến Java
        etStudentId = findViewById(R.id.et_student_id);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etClassName = findViewById(R.id.et_class_name);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);


        //Kiểm tra xem có dữ liệu sinh viên được gửi qua không (chế độ Sửa)
        if (getIntent().hasExtra("STUDENT_DATA")) {
            //Nếu có, lấy đối tượng Student và đặt tiêu đề cho màn hình
            studentToEdit = (Student) getIntent().getSerializableExtra("STUDENT_DATA");
            setTitle("Sửa thông tin sinh viên");
            //Điền thông tin cũ vào các ô nhập liệu
            populateFields();
        } else {
            //Nếu không, đây là chế độ Thêm mới
            setTitle("Thêm sinh viên mới");
        }

        //Gán sự kiện cho nút Lưu
        btnSave.setOnClickListener(v -> saveStudent());
        //Gán sự kiện cho nút Hủy, chỉ cần đóng màn hình hiện tại
        btnCancel.setOnClickListener(v -> finish());
    }


    //Điền dữ liệu của sinh viên đang sửa vào các ô EditText
    private void populateFields() {
        if (studentToEdit != null) {
            etStudentId.setText(studentToEdit.getStudentId());
            etName.setText(studentToEdit.getName());
            etEmail.setText(studentToEdit.getEmail());
            etClassName.setText(studentToEdit.getClassName());
        }
    }

    //Xử lý việc lưu thông tin sinh viên
    private void saveStudent() {
        //Lấy dữ liệu từ các ô EditText và loại bỏ khoảng trắng thừa
        String studentId = etStudentId.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String className = etClassName.getText().toString().trim();

        //Kiểm tra các trường bắt buộc không được để trống
        if (studentId.isEmpty() || name.isEmpty() || className.isEmpty()) {
            Toast.makeText(this, "Vui lòng cung cấp đầy đủ Họ Tên, MSSV và Lớp của sinh viên", Toast.LENGTH_SHORT).show();
            //Dừng hàm nếu dữ liệu không hợp lệ
            return;
        }

        //Tạo một Intent mới để chứa kết quả trả về cho MainActivity
        Intent resultIntent = new Intent();
        //Đưa dữ liệu người dùng nhập vào Intent dưới dạng các cặp key-value
        resultIntent.putExtra("studentId", studentId);
        resultIntent.putExtra("name", name);
        resultIntent.putExtra("email", email);
        resultIntent.putExtra("className", className);

        //Nếu là chế độ Sửa, gửi thêm ID của sinh viên về để biết cần cập nhật hàng nào
        if (studentToEdit != null) {
            resultIntent.putExtra("studentDbId", studentToEdit.getId());
        }

        //Đặt kết quả trả về là RESULT_OK và gửi kèm Intent chứa dữ liệu
        setResult(Activity.RESULT_OK, resultIntent);
        //Đóng Activity và quay về MainActivity
        finish();
    }
}
