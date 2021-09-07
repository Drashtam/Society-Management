package com.example.project.ui.login;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity{
    private LoginViewModel loginViewModel;
    Button loginButton;
    Button button;
    TextInputLayout password;
    TextInputLayout user;
    EditText passwordEditText;
    EditText phonetext;
    ProgressBar loadingProgressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory()).get(LoginViewModel.class);
        loginButton = findViewById(R.id.loginbtn);
        button = findViewById(R.id.loginwelcome);
        password = findViewById(R.id.textInputLayout2);
        user = findViewById(R.id.textInputLayout4);
        passwordEditText = findViewById(R.id.password);
        phonetext = findViewById(R.id.Phone2);
        loadingProgressBar = findViewById(R.id.loading);
        phonetext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if((phonetext.getText().toString().trim().length() == 10)){
                    user.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!passwordEditText.getText().toString().trim().isEmpty()){
                    password.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
            setResult(Activity.RESULT_OK);

            //Complete and destroy login activity once successful
            finish();
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(phonetext.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        findViewById(R.id.loginwelcome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Welcome_page.class));
                finish();
            }
        });
        findViewById(R.id.loginbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordEditText.getText().toString().trim().isEmpty()){
                    password.setError("Empty Field");
                }
                if(phonetext.getText().toString().trim().isEmpty()){
                    user.setError("Empty Field");
                }
                else if(!(phonetext.getText().toString().trim().length() == 10)){
                    user.setError("Invalid Number");
                }
                if(phonetext.getText().toString().trim().length() == 10){
                    confirm();
//                    SignupActivity sa = new SignupActivity();
//                    Toast.makeText(getApplicationContext(),"Welcome " + ,Toast.LENGTH_SHORT).show();
                }
            }
        });
        phonetext.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(phonetext.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        /*loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            loginViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
        });

         */


    }

    private void confirm(){
        FirebaseDatabase.getInstance().getReference("Society Member").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s2 = null;
                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("Phone").exists() && dataSnapshot.child("Password").exists()) {
                            if ((dataSnapshot.child("Phone").getValue(String.class).equals(phonetext.getText().toString().trim()) &&
                                    (dataSnapshot.child("Password").getValue(String.class).equals(passwordEditText.getText().toString().trim())))) {
                                if (dataSnapshot.exists()) {
                                    s2 = dataSnapshot.child("Password").getValue(String.class);
                                    user.setError(null);
                                    user.setErrorEnabled(false);
                                    password.setError(null);
                                    password.setErrorEnabled(false);
                                    Intent intent = new Intent(getApplicationContext(), userdashboard.class);
                                    Toast.makeText(getApplicationContext(), "Welcome " + dataSnapshot.child("Username").getValue(String.class), Toast.LENGTH_SHORT).show();
                                    intent.putExtra("nameuser", dataSnapshot.child("Username").getValue(String.class));
                                    intent.putExtra("phoneuser", dataSnapshot.child("Phone").getValue(String.class));
                                    startActivity(intent);
                                    break;
                                }
                            }
                        }
                    }
                    if(s2 == null && !(passwordEditText.getText().toString().trim().isEmpty())){
                        Toast.makeText(getApplicationContext(),"Phone or Password might be incorrect",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateUiWithUser(LoggedInUserView model) {
//        String welcome = getString(R.string.hello) + " " + model.getDisplayName();
        // TODO : initiate successful logged in experience
//        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }
    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
//class User {
//    String  Username,Password,Phone;
//    User(){
//
//    }
//
//    public User(String Username,String Phone,String Password){
//        this.Username = Username;
//        this.Phone = Phone;
//        this.Password = Password;
//    }
//
//    public String getUsername(){
//        return Username;
//    }
//
//    public  void setUsername(String Username){
//        this.Username = Username;
//    }
//
//    public String getPassword(){
//        return  Password;
//    }
//
//    public void setPassword(String Password){
//        this.Password = Password;
//    }
//
//    public String getPhone(){
//        return Phone;
//    }
//
//    public void setPhone(String Phone){
//        this.Phone = Phone;
//    }
//
//}