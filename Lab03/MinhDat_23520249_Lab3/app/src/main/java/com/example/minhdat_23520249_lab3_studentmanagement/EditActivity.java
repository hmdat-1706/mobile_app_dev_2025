package com.example.minhdat_23520249_lab3_studentmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    private EditText etStudentId, etName, etEmail, etClassName;
    private Button btnSave;
    private Student studentToEdit; // Sinh viên cần sửa, null nếu là thêm mới

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etStudentId = findViewById(R.id.et_student_id);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etClassName = findViewById(R.id.et_class_name);
        btnSave = findViewById(R.id.btn_save);

        // Kiểm tra xem có dữ liệu sinh viên được gửi qua không (chế độ Sửa)
        if (getIntent().hasExtra("STUDENT_DATA")) {
            studentToEdit = (Student) getIntent().getSerializableExtra("STUDENT_DATA");
            setTitle("Sửa thông tin sinh viên");
            populateFields();
        } else {
            setTitle("Thêm sinh viên mới");
        }

        btnSave.setOnClickListener(v -> saveStudent());
    }


    private void populateFields() {
        if (studentToEdit != null) {
            etStudentId.setText(studentToEdit.getStudentId());
            etName.setText(studentToEdit.getName());
            etEmail.setText(studentToEdit.getEmail());
            etClassName.setText(studentToEdit.getClassName());
        }
    }

    private void saveStudent() {
        String studentId = etStudentId.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String className = etClassName.getText().toString().trim();

        if (studentId.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Mã số sinh viên và Tên không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("studentId", studentId);
        resultIntent.putExtra("name", name);
        resultIntent.putExtra("email", email);
        resultIntent.putExtra("className", className);

        if (studentToEdit != null) {
            // Gửi ID của sinh viên cần sửa về
            resultIntent.putExtra("studentDbId", studentToEdit.getId());
        }

        setResult(Activity.RESULT_OK, resultIntent);
        finish(); // Đóng Activity và quay về MainActivity
    }
}
    