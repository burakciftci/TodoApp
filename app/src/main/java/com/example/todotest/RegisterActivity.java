package com.example.todotest;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todotest.db.SqliteHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    SqliteHelper sqliteHelper;

    EditText etNameS;
    EditText etEmailS;
    EditText etPasswordS;
    Button btnSignUpS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regiter_activity);
        etNameS = findViewById(R.id.etNameS);
        etEmailS = findViewById(R.id.etEmailS);
        etPasswordS = findViewById(R.id.etPasswordS);
        btnSignUpS = findViewById(R.id.btnSignUpS);

        sqliteHelper = new SqliteHelper(this);

        btnSignUpS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = etNameS.getText().toString();
                    String Email = etEmailS.getText().toString();
                    String Password = etPasswordS.getText().toString();
                    if (!sqliteHelper.isEmailExists(Email)) {
                        sqliteHelper.addUser(new User(null, UserName, Email, Password));
                        Toast.makeText(getApplicationContext(),"User Created succesful",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);

                    }else {
                        Toast.makeText(getApplicationContext(),"User already exists with same email",Toast.LENGTH_LONG).show();

                    }
                }
            }

            private boolean validate() {

                boolean valid = false;
                String UserName = etNameS.getText().toString();
                String Email = etEmailS.getText().toString();
                String Password = etPasswordS.getText().toString();
                if (UserName.isEmpty()) {
                    valid = false;
                    etNameS.setError("Please enter valid username!");
                } else {
                    if (UserName.length() > 5) {
                        valid = true;
                        etNameS.setError(null);
                    } else {
                        valid = false;
                        etNameS.setError("Username is to short!");
                    }
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    valid = false;
                    etEmailS.setError("Please enter valid email!");
                } else {
                    valid = true;
                    etEmailS.setError(null);
                }

                if (Password.isEmpty()) {
                    valid = false;
                    etPasswordS.setError("Please enter valid password!");
                } else {
                    if (Password.length() > 5) {
                        valid = true;
                        etPasswordS.setError(null);
                    } else {
                        valid = false;
                        etPasswordS.setError("Password is to short!");
                    }
                }
                return valid;
            }
        });
    }


}

