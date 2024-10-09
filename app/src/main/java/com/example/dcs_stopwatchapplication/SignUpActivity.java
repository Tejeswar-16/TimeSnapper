package com.example.dcs_stopwatchapplication;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
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
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity
{
    EditText etName,etSignUpEmail,etSignupPassword,etSignupConfirmPassword;
    ImageView ivSignUp;
    TextView tvAULF;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        etName = (EditText) findViewById(R.id.etName);
        etSignUpEmail = (EditText) findViewById(R.id.etSignUpEmail);
        etSignupPassword = (EditText) findViewById(R.id.etSignupPassword);
        etSignupConfirmPassword = (EditText) findViewById(R.id.etSignupConfirmPassword);
        ivSignUp = (ImageView) findViewById(R.id.ivSignUp);
        tvAULF = (TextView) findViewById(R.id.tvAULF);

        ivSignUp.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {
                String suname = etName.getText().toString();
                String semail = etSignUpEmail.getText().toString();
                String spw = etSignupPassword.getText().toString();
                String scpw = etSignupConfirmPassword.getText().toString();

                if (suname.isEmpty()) {
                    etName.setError("Username is mandatory");
                    return;
                }
                if (semail.isEmpty()) {
                    etSignUpEmail.setError("Email is mandatory");
                    return;
                }
                if (spw.isEmpty()) {
                    etSignupPassword.setError("Password is mandatory");
                    return;
                }
                if (scpw.isEmpty()) {
                    etSignupConfirmPassword.setError("Confirm Password is mandatory");
                    return;
                }
                if (!isValidEmail(semail)) {
                    etSignUpEmail.setError("Invalid Email");
                    return;
                }
                if (!isValidPassword(spw)) {
                    etSignupPassword.setError("Invalid Password - It must contain atleast one uppercase, one lowercase, one digit, one special character and it must be in the range of 8 - 12");
                    return;
                }
                if (!isValidPassword(scpw)) {
                    etSignupConfirmPassword.setError("Invalid Confirm Password - It must contain atleast one uppercase, one lowercase, one digit, one special character and it must be in the range of 8 - 12");
                    return;
                }
                if (!spw.equals(scpw)) {
                    etSignupConfirmPassword.setError("Password and Confirm Password didn't match");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(semail, spw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(SignUpActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        tvAULF.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
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