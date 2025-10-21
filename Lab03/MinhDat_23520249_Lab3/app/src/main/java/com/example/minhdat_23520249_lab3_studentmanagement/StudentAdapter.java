package com.example.minhdat_23520249_lab3_studentmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    private final OnItemClickListener listener;

    //Interface để xử lý các sự kiện click
    public interface OnItemClickListener {
        void onItemClick(Student student);
        void onEditClick(Student student);
        void onDeleteClick(Student student);
    }

    public StudentAdapter(List<Student> studentList, OnItemClickListener listener) {
        this.studentList = studentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student currentStudent = studentList.get(position);
        holder.bind(currentStudent, listener);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    //Cập nhật lại danh sách và giao diện
    public void updateData(List<Student> newStudentList) {
        this.studentList = newStudentList;
        notifyDataSetChanged();
    }

    //ViewHolder để giữ các View của một item
    static class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvStudentId;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvStudentId = itemView.findViewById(R.id.tv_student_id);
        }

        public void bind(final Student student, final OnItemClickListener listener) {
            tvName.setText(student.getName());
            tvStudentId.setText("MSSV: " + student.getStudentId());

            //Gán sự kiện cho cả item view
            itemView.setOnClickListener(v -> listener.onItemClick(student));

            //Bắt sự kiện nhấn giữ (long click) để hiện menu Sửa/Xóa
            itemView.setOnLongClickListener(v -> {
                //Tạo một popup menu
                android.widget.PopupMenu popup = new android.widget.PopupMenu(itemView.getContext(), itemView);
                popup.getMenuInflater().inflate(R.menu.menu_context, popup.getMenu());
                popup.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.menu_edit) {
                        listener.onEditClick(student);
                        return true;
                    } else if (itemId == R.id.menu_delete) {
                        listener.onDeleteClick(student);
                        return true;
                    }
                    return false;
                });
                popup.show();
                return true; //Đánh dấu sự kiện đã được xử lý
            });
        }
    }
}