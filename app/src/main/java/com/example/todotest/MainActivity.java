package com.example.todotest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todotest.db.SqliteHelper;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnSingUp;
    EditText etEmail;
    EditText etPassword;
    SqliteHelper sqliteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogın);
        btnSingUp = findViewById(R.id.btnSignUpS);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        sqliteHelper = new SqliteHelper(this);
        initCreateAccountTextView();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    String Email = etEmail.getText().toString();
                    String Password = etPassword.getText().toString();
                    User currentUser = sqliteHelper.Authenticate(new User(null, null, Email, Password));
                    if (currentUser != null) {
                        Toast.makeText(getApplicationContext(),"Login succesful",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, TodoActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),"Login not succesful",Toast.LENGTH_LONG).show();

                    }
                }
            }
            private boolean validate() {
                boolean valid = false;
                String Email = etEmail.getText().toString();
                String Password = etPassword.getText().toString();
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    valid = false;
                    etEmail.setError("Please enter valid email!");
                } else {
                    valid = true;
                    etEmail.setError(null);
                }
                if (Password.isEmpty()) {
                    valid = false;
                    etPassword.setError("Please enter valid password!");
                } else {
                    if (Password.length() > 5) {
                        valid = true;
                        etPassword.setError(null);
                    } else {
                        valid = false;
                        etPassword.setError("Password is to short!");
                    }
                }

                return valid;
            }
        });


    }


    private void initCreateAccountTextView() {
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


}
