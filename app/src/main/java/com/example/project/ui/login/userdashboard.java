package com.example.project.ui.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;

public class userdashboard extends AppCompatActivity {
    AlertDialog.Builder builder;
    LayoutInflater layoutInflater;
    View view;
    TextView view1,view2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdashboard);
        layoutInflater = getLayoutInflater();
        view = layoutInflater.inflate(R.layout.activity_userinfo,null);
        view1 = view.findViewById(R.id.name_of_user);
        view2 = view.findViewById(R.id.phone_of_user);

        String nameuser = getIntent().getStringExtra("nameuser");
        String phoneuser = getIntent().getStringExtra("phoneuser");

        view1.setText("Name : " + nameuser);
        view2.setText("Phone : " + phoneuser);

        findViewById(R.id.gotouser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(userdashboard.this);
                builder.setView(view);
                builder.setTitle("User Details");
                builder.setCancelable(false);
                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        findViewById(R.id.issuessec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),drawer.class);
                intent.putExtra("phoneofuser",nameuser);
                startActivity(intent);
            }
        });

        findViewById(R.id.usertolog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        findViewById(R.id.emergency).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),emetext.class));
            }
        });

        findViewById(R.id.societydet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),societydetails.class);
                intent.putExtra("nameu",nameuser);
                intent.putExtra("phoneu",phoneuser);
                startActivity(intent);
            }
        });
        findViewById(R.id.s).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),mettings.class));
            }
        });
    }
}