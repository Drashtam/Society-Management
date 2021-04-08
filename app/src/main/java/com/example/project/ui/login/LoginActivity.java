package com.example.project.ui.login;
import android.app.Activity;
import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.project.R;
import com.example.project.Welcome_page;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity{
    private LoginViewModel loginViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory()).get(LoginViewModel.class);
        final TextInputLayout password = findViewById(R.id.textInputLayout2);
        final TextInputLayout user = findViewById(R.id.textInputLayout4);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText usernameEditText = findViewById(R.id.Username);
        final Button loginButton = findViewById(R.id.loginbtn);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final Button button = findViewById(R.id.login_welcome);
        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
//                usernameEditText.setError(getString(loginFormState.getUsernameError()));
//                user.setError(getString(loginFormState.getUsernameError()));
                return;

            }
            if (loginFormState.getPasswordError() != null) {
//                passwordEditText.setError(getString(loginFormState.getPasswordError()));
//                password.setError("Invalid Password");
                return ;
            }
        });
        /*usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(usernameEditText.getText().toString().isEmpty()){
                    usernameEditText.requestFocus();
                    user.requestFocus();
                    loginButton.setEnabled(false);
                }
            }
        });


        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(passwordEditText.getText().toString().trim().isEmpty()){
                    password.requestFocus();
                    passwordEditText.requestFocus();
                    loginButton.setEnabled(false);
                }
            }
        });

         */

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

        button.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, Welcome_page.class));
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
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        loginButton.setOnClickListener(v -> {
            if(TextUtils.isEmpty(usernameEditText.getText().toString())){
                user.setError("Empty Field");
            }
            if(TextUtils.isEmpty(passwordEditText.getText().toString())){
                password.setError("Empty Field");
            }
            if(!usernameEditText.getText().toString().trim().isEmpty() && passwordEditText.getText().toString().trim().length() >= 10){
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(),
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
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.hello) + " " + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }
    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}