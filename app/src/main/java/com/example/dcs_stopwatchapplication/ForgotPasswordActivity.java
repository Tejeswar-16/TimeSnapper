package com.example.dcs_stopwatchapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Iterator;

public class ForgotPasswordActivity extends AppCompatActivity
{
    EditText etResetEmail;
    Button btnRP;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        etResetEmail = (EditText) findViewById(R.id.etResetEmail);
        btnRP = (Button) findViewById(R.id.btnRP);


        btnRP.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                String email = etResetEmail.getText().toString();
                if (email.isEmpty()) {
                    etResetEmail.setError("Email is mandatory");
                    return;
                }

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(ForgotPasswordActivity.this,"Password reset link has been sent to your registered email",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                                    finish();
                                }
                                else
                                {
                                    etResetEmail.setError("Email ID does not exist");
                                }
                            }
                });
            }
        });
    }
}