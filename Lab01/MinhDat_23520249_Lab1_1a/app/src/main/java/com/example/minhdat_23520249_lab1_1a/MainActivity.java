package com.example.minhdat_23520249_lab1_1a;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout llNameContainer, llAddressContainer, llParentContainer;
    private void createNameContainer() {
        llNameContainer = new LinearLayout(this);
        llNameContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        llNameContainer.setOrientation(LinearLayout.HORIZONTAL);
        TextView tvName = new TextView(this);
        tvName.setText("Name: ");
        llNameContainer.addView(tvName);
        TextView tvNameValue = new TextView(this);
        tvNameValue.setText("John Doe");
        llNameContainer.addView(tvNameValue);
    }
    private void createAddressContainer() {
        llAddressContainer = new LinearLayout(this);
        llAddressContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        llAddressContainer.setOrientation(LinearLayout.HORIZONTAL);
        TextView tvAddress = new TextView(this);
        tvAddress.setText("Address:");
        llAddressContainer.addView(tvAddress);
        TextView tvAddressValue = new TextView(this);
        tvAddressValue.setText(" 911 Hollywood Blvd");
        llAddressContainer.addView(tvAddressValue);
    }
    private void createParentContainer() {
        llParentContainer = new LinearLayout(this);
        llParentContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        llParentContainer.setOrientation(LinearLayout.VERTICAL);
        llParentContainer.addView(llNameContainer);
        llParentContainer.addView(llAddressContainer);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createNameContainer();
        createAddressContainer();
        createParentContainer();

        // Thêm padding để tránh đè lên status bar
        llParentContainer.setPadding(50, 400, 16, 16);

        setContentView(llParentContainer);
    }
}