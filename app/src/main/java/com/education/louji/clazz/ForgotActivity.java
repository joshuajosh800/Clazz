package com.education.louji.clazz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import at.markushi.ui.CircleButton;

public class ForgotActivity extends AppCompatActivity {
private TextInputEditText forgotpassword;
private CircleButton forgotbutton;
private TextView signin;
private FirebaseAuth auth;
private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        //Casting the variables
        forgotpassword=findViewById(R.id.forgotfireemail);
        forgotbutton=findViewById(R.id.forgotbutton);
        signin=findViewById(R.id.returnloginforgot);
        //get firebase auth instance
        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        //once forgot password button is clicked the method will be called
        forgotbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailsentprocess();
            }
        });
        //once forgot login text is clicked the method will be called
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ForgotActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void emailsentprocess() {
        String email=forgotpassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            forgotpassword.setError("Email can not be left empty");
        }else {
            progressDialog.setMessage("Contacting the server....");
            progressDialog.show();
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgotActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }
}
