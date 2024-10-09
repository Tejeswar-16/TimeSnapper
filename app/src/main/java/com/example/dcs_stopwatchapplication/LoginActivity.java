package com.example.dcs_stopwatchapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity
{
    EditText etEmail,etPassword;
    ImageView ivLogin;
    TextView tvNUSU,tvForgotPassword;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        ivLogin = (ImageView) findViewById(R.id.ivLogin);
        tvNUSU = (TextView) findViewById(R.id.tvNUSU);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);

        ivLogin.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("Email is mandatory");
                    return;
                }
                if (etPassword.getText().toString().isEmpty()){
                    etPassword.setError("Password is mandatory");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email,password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this,CaptureActivity.class));
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(LoginActivity.this,"Incorrect Email or Password",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });

        tvNUSU.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
    }

    public boolean isValidEmail(String email)
    {
        String cemail = "[A-Za-z0-9_-]+@+[a-z]+.+[a-z]";
        return  email.matches(cemail);
    }

    public boolean isValidPassword (String password)
    {
        String cpassword = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_.]).{8,12}$";
        return password.matches(cpassword);
    }
}