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

        //Khởi tạo DatabaseHelper để quản lý cơ sở dữ liệu
        dbHelper = new DatabaseHelper(this);

        //Ánh xạ các view từ file layout XML vào biến Java
        rvStudents = findViewById(R.id.rv_students);
        fabAdd = findViewById(R.id.fab_add);

        //Khởi tạo danh sách và thiết lập cho RecyclerView
        studentList = new ArrayList<>();
        setupRecyclerView();
        //Tải dữ liệu từ database lên danh sách ngay khi mở ứng dụng
        loadStudentsFromDb();

        //Xử lý sự kiện khi người dùng nhấn nút "Thêm"
        fabAdd.setOnClickListener(v -> {
            //Tạo một Intent để mở màn hình EditActivity
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            //Khởi chạy EditActivity và chờ kết quả trả về
            addEditLauncher.launch(intent);
        });
    }

    //Cài đặt các thuộc tính cần thiết cho RecyclerView
    private void setupRecyclerView() {
        //Khởi tạo Adapter và định nghĩa các hành động cho từng item
        studentAdapter = new StudentAdapter(studentList, new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Student student) {
                //Khi nhấn vào một item, hiển thị hộp thoại chi tiết
                showStudentDetails(student);
            }

            @Override
            public void onEditClick(Student student) {
                //Khi nhấn nút "Sửa", mở màn hình EditActivity và gửi dữ liệu của sinh viên
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("STUDENT_DATA", student);
                addEditLauncher.launch(intent);
            }

            @Override
            public void onDeleteClick(Student student) {
                //Khi nhấn nút "Xóa", hiển thị hộp thoại xác nhận
                confirmDeleteStudent(student);
            }
        });
        //Sắp xếp các item trong RecyclerView theo chiều dọc
        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        //Gắn adapter vào RecyclerView để hiển thị dữ liệu
        rvStudents.setAdapter(studentAdapter);
    }

    //Tải toàn bộ danh sách sinh viên từ SQLite Database
    private void loadStudentsFromDb() {
        //Lấy quyền đọc cơ sở dữ liệu
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //Xóa danh sách hiện tại để tránh lặp lại dữ liệu khi tải lại
        studentList.clear();
        //Thực hiện truy vấn để lấy tất cả sinh viên, sắp xếp theo tên
        Cursor cursor = database.query(DatabaseHelper.TABLE_STUDENTS, null, null, null, null, null, DatabaseHelper.COLUMN_NAME + " ASC");

        if (cursor != null) {
            //Di chuyển con trỏ đến hàng dữ liệu đầu tiên
            if (cursor.moveToFirst()) {
                //Lấy chỉ số (index) của các cột
                int idCol = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
                int studentIdCol = cursor.getColumnIndex(DatabaseHelper.COLUMN_STUDENT_ID);
                int nameCol = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
                int emailCol = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL);
                int classCol = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLASS_NAME);

                //Dùng vòng lặp do-while để đọc dữ liệu từ từng hàng
                do {
                    long id = cursor.getLong(idCol);
                    String studentId = cursor.getString(studentIdCol);
                    String name = cursor.getString(nameCol);
                    String email = cursor.getString(emailCol);
                    String className = cursor.getString(classCol);
                    //Tạo đối tượng Student và thêm vào danh sách
                    studentList.add(new Student(id, studentId, name, email, className));
                } while (cursor.moveToNext()); //Di chuyển đến hàng tiếp theo
            }
            cursor.close();
        }
        //Thông báo cho adapter biết dữ liệu đã thay đổi để nó cập nhật lại giao diện
        studentAdapter.updateData(studentList);
    }

    //Xử lý kết quả trả về từ EditActivity (sau khi Thêm hoặc Sửa)
    private final ActivityResultLauncher<Intent> addEditLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                //Kiểm tra xem thao tác có thành công và có dữ liệu trả về không
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    long studentDbId = data.getLongExtra("studentDbId", -1);

                    //Lấy quyền ghi cơ sở dữ liệu
                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    //Đóng gói dữ liệu mới vào một ContentValues
                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.COLUMN_STUDENT_ID, data.getStringExtra("studentId"));
                    values.put(DatabaseHelper.COLUMN_NAME, data.getStringExtra("name"));
                    values.put(DatabaseHelper.COLUMN_EMAIL, data.getStringExtra("email"));
                    values.put(DatabaseHelper.COLUMN_CLASS_NAME, data.getStringExtra("className"));

                    //Nếu không có ID, đây là trường hợp THÊM MỚI
                    if (studentDbId == -1) {
                        database.insert(DatabaseHelper.TABLE_STUDENTS, null, values);
                        Toast.makeText(this, "Thêm thông tin sinh viên thành công", Toast.LENGTH_SHORT).show();
                    } else { //Nếu có ID, đây là trường hợp CẬP NHẬT
                        database.update(DatabaseHelper.TABLE_STUDENTS, values, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(studentDbId)});
                        Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    }
                    //Tải lại danh sách từ database để hiển thị dữ liệu mới nhất
                    loadStudentsFromDb();
                }
            });

    //Hiển thị chi tiết sinh viên bằng một hộp thoại AlertDialog
    private void showStudentDetails(Student student) {
        new AlertDialog.Builder(this)
                .setTitle("Chi tiết thông tin sinh viên")
                .setMessage("MSSV: " + student.getStudentId() +
                        "\nHọ và tên: " + student.getName() +
                        "\nEmail: " + student.getEmail() +
                        "\nLớp: " + student.getClassName())
                //Nút "Đóng" không cần hành động gì thêm
                .setPositiveButton("Đóng", null)
                .show();
    }

    //Hiển thị thông báo xác nhận trước khi xóa một sinh viên
    private void confirmDeleteStudent(final Student student) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa sinh viên")
                .setMessage("Bạn có chắc chắn muốn xóa thông tin sinh viên " + student.getName() + "?")
                //Nút xác nhận thực hiện hành động xóa
                .setPositiveButton("Xóa", (dialog, which) -> {
                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    database.delete(DatabaseHelper.TABLE_STUDENTS, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(student.getId())});
                    Toast.makeText(MainActivity.this, "Đã xóa thông tin sinh viên", Toast.LENGTH_SHORT).show();
                    //Tải lại dữ liệu sau khi xóa để cập nhật giao diện
                    loadStudentsFromDb();
                })
                //Nút hủy không làm gì, hộp thoại sẽ tự đóng
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        //Đóng kết nối database khi ứng dụng bị terminate
        dbHelper.close();
        super.onDestroy();
    }
}
