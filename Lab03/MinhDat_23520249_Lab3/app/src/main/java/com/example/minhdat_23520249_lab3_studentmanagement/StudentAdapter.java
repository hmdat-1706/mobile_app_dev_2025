package com.example.minhdat_23520249_lab3_studentmanagement;import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    //Khai báo danh sách dữ liệu và interface xử lý sự kiện
    private List<Student> studentList;
    private final OnItemClickListener listener;

    //Interface để định nghĩa các hành động click sẽ được xử lý ở MainActivity
    public interface OnItemClickListener {
        void onItemClick(Student student);
        void onEditClick(Student student);
        void onDeleteClick(Student student);
    }

    //Constructor để nhận dữ liệu và listener từ MainActivity
    public StudentAdapter(List<Student> studentList, OnItemClickListener listener) {
        this.studentList = studentList;
        this.listener = listener;
    }

    //Được gọi khi RecyclerView cần một ViewHolder mới để hiển thị một item
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    //Được gọi để liên kết dữ liệu từ một vị trí trong danh sách với một ViewHolder
    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        //Lấy sinh viên hiện tại từ danh sách
        Student currentStudent = studentList.get(position);
        //Gắn dữ liệu và các sự kiện vào view của ViewHolder
        holder.bind(currentStudent, listener);
    }

    //Trả về tổng số item trong danh sách
    @Override
    public int getItemCount() {
        return studentList.size();
    }

    //Phương thức công khai để cập nhật lại danh sách dữ liệu và giao diện
    public void updateData(List<Student> newStudentList) {
        this.studentList = newStudentList;
        //Thông báo cho adapter rằng toàn bộ dữ liệu đã thay đổi để vẽ lại RecyclerView
        notifyDataSetChanged();
    }

    //Lớp ViewHolder đại diện cho giao diện của một item trong danh sách
    static class StudentViewHolder extends RecyclerView.ViewHolder {
        //Khai báo các view có trong một item
        private final TextView tvName;
        private final TextView tvStudentId;

        //Constructor của ViewHolder, dùng để ánh xạ các view
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvStudentId = itemView.findViewById(R.id.tv_student_id);
        }

        //Phương thức để gán dữ liệu và sự kiện cho các view bên trong item
        public void bind(final Student student, final OnItemClickListener listener) {
            //Gán dữ liệu văn bản cho các TextView
            tvName.setText(student.getName());
            tvStudentId.setText("MSSV: " + student.getStudentId());

            //Gán sự kiện click cho toàn bộ view của item
            itemView.setOnClickListener(v -> listener.onItemClick(student));

            //Bắt sự kiện nhấn giữ (long click) để hiện menu Sửa/Xóa
            itemView.setOnLongClickListener(v -> {
                //Tạo một popup menu tại vị trí của item được nhấn giữ
                android.widget.PopupMenu popup = new android.widget.PopupMenu(itemView.getContext(), itemView);
                //Load menu từ file menu_context.xml
                popup.getMenuInflater().inflate(R.menu.menu_context, popup.getMenu());
                //Xử lý sự kiện khi một mục trong menu được chọn
                popup.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.menu_edit) {
                        //Nếu chọn "Sửa", gọi phương thức onEditClick của listener
                        listener.onEditClick(student);
                        return true;
                    } else if (itemId == R.id.menu_delete) {
                        //Nếu chọn "Xóa", gọi phương thức onDeleteClick của listener
                        listener.onDeleteClick(student);
                        return true;
                    }
                    return false;
                });
                //Hiển thị popup menu
                popup.show();
                return true; //Trả về true để báo rằng sự kiện đã được xử lý
            });
        }
    }
}
