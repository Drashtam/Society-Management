package com.example.project;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.project.ui.login.LoginActivity;
import com.example.project.ui.login.SignupActivity;

public class Welcome_page extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Button button = findViewById(R.id.wel);
        button.setOnClickListener(v -> {
            startActivity(new Intent(Welcome_page.this,LoginActivity.class));
            finish();
        });
        Button button1 = (Button) findViewById(R.id.signup);
        button1.setOnClickListener(v -> {
            startActivity(new Intent(Welcome_page.this, SignupActivity.class));
            finish();
        });
    }
}