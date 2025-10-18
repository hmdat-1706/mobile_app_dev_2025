package com.example.minhdat_23520249_lab2_6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private Context context;
    private ArrayList<Employee> employeeList;

    public EmployeeAdapter(Context context, ArrayList<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.tvFullName.setText(employee.getFullName());

        //Xử lý hiển thị Staff/Icon Manager
        if (employee.isManager()) {
            holder.ivManager.setVisibility(View.VISIBLE);
            holder.tvStaff.setVisibility(View.GONE);
        } else {
            holder.ivManager.setVisibility(View.GONE);
            holder.tvStaff.setVisibility(View.VISIBLE);
        }

        //Xử lý đổi màu nền xen kẽ
        if (position % 2 == 0) { // Vị trí chẵn
            holder.itemLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        } else { //Vị trí lẻ
            holder.itemLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue));
        }
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvStaff;
        ImageView ivManager;
        RelativeLayout itemLayout;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tv_fullName);
            tvStaff = itemView.findViewById(R.id.tv_staff);
            ivManager = itemView.findViewById(R.id.iv_manager);
            itemLayout = itemView.findViewById(R.id.item_layout);
        }
    }
}
    