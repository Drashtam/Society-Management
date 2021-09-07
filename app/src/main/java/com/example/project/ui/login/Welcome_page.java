package com.example.project.ui.login;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;

public class Welcome_page extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Button button1 = (Button) findViewById(R.id.wel);
        TextView tt = findViewById(R.id.tosignup);
        button1.setOnClickListener(v -> {
            startActivity(new Intent(Welcome_page.this, LoginActivity.class));
            finish();
        });
        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Welcome_page.this,SignupActivity.class));
                finish();
            }
        });
    }
}