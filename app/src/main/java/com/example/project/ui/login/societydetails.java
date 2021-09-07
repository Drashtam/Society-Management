package com.example.project.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class societydetails extends AppCompatActivity {

    TextInputLayout sc1,h1,p1,a1,c1,s1;
    EditText sc2,h2,p2,a2,c2,s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_societydetails);

        sc1 = findViewById(R.id.societyname1);
        h1 = findViewById(R.id.house1);
        p1 = findViewById(R.id.pincode1);
        a1 = findViewById(R.id.area1);
        c1 = findViewById(R.id.city1);
        s1 = findViewById(R.id.state1);

        sc2 = findViewById(R.id.societyname2);
        h2 = findViewById(R.id.house2);
        p2 = findViewById(R.id.pincode2);
        a2 = findViewById(R.id.area2);
        c2 = findViewById(R.id.city2);
        s2 = findViewById(R.id.state2);

        String nameu = getIntent().getStringExtra("nameu");
        String phoneu = getIntent().getStringExtra("phoneu");

        findViewById(R.id.savebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<>();
                map.put("SocietyName",sc2.getText().toString().trim());
                map.put("HouseNo.",h2.getText().toString().trim());
                map.put("Pincode",p2.getText().toString().trim());
                map.put("Area",a2.getText().toString().trim());
                map.put("City",c2.getText().toString().trim());
                map.put("State",s2.getText().toString().trim());

                FirebaseDatabase.getInstance().getReference("Society Member").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.child("Phone").exists() && dataSnapshot.child("Password").exists()) {
                                    if ((dataSnapshot.child("Phone").getValue(String.class).equals(nameu) &&
                                            (dataSnapshot.child("Password").getValue(String.class).equals(phoneu)))) {
                                        if (dataSnapshot.exists()) {
                                            FirebaseDatabase.getInstance().getReference("Society Member").push()
                                                    .setValue(map)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}