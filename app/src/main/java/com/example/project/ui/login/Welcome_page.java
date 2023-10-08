package com.example.project.ui.login;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;

public class Welcome_page extends AppCompatActivity {
    Button button1;
    TextView tt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        button1 = (Button) findViewById(R.id.wel);
        tt = findViewById(R.id.tosignup);
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
