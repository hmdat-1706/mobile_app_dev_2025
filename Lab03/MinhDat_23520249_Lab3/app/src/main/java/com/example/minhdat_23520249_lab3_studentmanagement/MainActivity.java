package com.example.minhdat_23520249_lab3_studentmanagement;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvStudents;
    private FloatingActionButton fabAdd;
    private StudentAdapter studentAdapter;
    private List<Student> studentList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        //Ánh xạ views
        rvStudents = findViewById(R.id.rv_students);
        fabAdd = findViewById(R.id.fab_add);

        //Khởi tạo danh sách và thiết lập RecyclerView
        studentList = new ArrayList<>();
        setupRecyclerView();
        loadStudentsFromDb();

        //Xử lý sự kiện cho nút Add
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            addEditLauncher.launch(intent);
        });
    }

    private void setupRecyclerView() {
        studentAdapter = new StudentAdapter(studentList, new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Student student) {
                showStudentDetails(student);
            }

            @Override
            public void onEditClick(Student student) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("STUDENT_DATA", student);
                addEditLauncher.launch(intent);
            }

            @Override
            public void onDeleteClick(Student student) {
                confirmDeleteStudent(student);
            }
        });
        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        rvStudents.setAdapter(studentAdapter);
    }

    private void loadStudentsFromDb() {
        //Lấy database chỉ khi cần dùng
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        studentList.clear();
        Cursor cursor = database.query(DatabaseHelper.TABLE_STUDENTS, null, null, null, null, null, DatabaseHelper.COLUMN_NAME + " ASC");

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idCol = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
                int studentIdCol = cursor.getColumnIndex(DatabaseHelper.COLUMN_STUDENT_ID);
                int nameCol = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
                int emailCol = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL);
                int classCol = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLASS_NAME);

                do {
                    long id = cursor.getLong(idCol);
                    String studentId = cursor.getString(studentIdCol);
                    String name = cursor.getString(nameCol);
                    String email = cursor.getString(emailCol);
                    String className = cursor.getString(classCol);
                    studentList.add(new Student(id, studentId, name, email, className));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        //Cập nhật lại giao diện của Adapter
        studentAdapter.updateData(studentList);
    }

    private final ActivityResultLauncher<Intent> addEditLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    long studentDbId = data.getLongExtra("studentDbId", -1);

                    //Lấy database chỉ khi cần dùng
                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.COLUMN_STUDENT_ID, data.getStringExtra("studentId"));
                    values.put(DatabaseHelper.COLUMN_NAME, data.getStringExtra("name"));
                    values.put(DatabaseHelper.COLUMN_EMAIL, data.getStringExtra("email"));
                    values.put(DatabaseHelper.COLUMN_CLASS_NAME, data.getStringExtra("className"));

                    if (studentDbId == -1) {
                        database.insert(DatabaseHelper.TABLE_STUDENTS, null, values);
                        Toast.makeText(this, "Thêm thông tin sinh viên thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        database.update(DatabaseHelper.TABLE_STUDENTS, values, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(studentDbId)});
                        Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    }
                    loadStudentsFromDb();
                }
            });

    private void showStudentDetails(Student student) {
        new AlertDialog.Builder(this)
                .setTitle("Chi tiết thông tin sinh viên")
                .setMessage("MSSV: " + student.getStudentId() +
                        "\nHọ và tên: " + student.getName() +
                        "\nEmail: " + student.getEmail() +
                        "\nLớp: " + student.getClassName())
                .setPositiveButton("Đóng", null)
                .show();
    }

    private void confirmDeleteStudent(final Student student) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa sinh viên")
                .setMessage("Bạn có chắc chắn muốn xóa thông tin sinh viên " + student.getName() + "?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    database.delete(DatabaseHelper.TABLE_STUDENTS, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(student.getId())});
                    Toast.makeText(MainActivity.this, "Đã xóa thông tin sinh viên", Toast.LENGTH_SHORT).show();
                    //Tải lại dữ liệu sau khi xóa
                    loadStudentsFromDb();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close(); //Đóng kết nối database khi ứng dụng tắt
        super.onDestroy();
    }
}
