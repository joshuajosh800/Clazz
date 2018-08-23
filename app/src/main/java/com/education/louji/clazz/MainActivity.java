package com.education.louji.clazz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.victor.loading.rotate.RotateLoading;

import at.markushi.ui.CircleButton;

public class MainActivity extends AppCompatActivity {
private CircleButton login;
private TextInputEditText email;
private TextInputEditText password;
private TextView forgot;
private FirebaseAuth auth;
ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Casting the variables
        login=findViewById(R.id.loginb);
        email=findViewById(R.id.fireemail);
        password=findViewById(R.id.firepassword);
        progressBar=new ProgressDialog(this);
        forgot=findViewById(R.id.forgotlogin);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //onclick listener for the login button.  once the login button is clicked this method gets started
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginprocess();
           }
        });
        //Once the forgot password text is clicked this method will be called
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,ForgotActivity.class);
                startActivity(i);
            }
        });
        //Checks if there is any current user signned in.  If so directly the Profile page will be displayed
        if (auth.getCurrentUser() != null) {
            Intent intent=new Intent(MainActivity.this,Student.class);
            startActivity(intent);
        }
    }

    private void loginprocess() {
        String fireemail = email.getText().toString().trim();
        String firepassword = password.getText().toString().trim();

        if (TextUtils.isEmpty(fireemail)&& TextUtils.isEmpty(firepassword)) {
            email.setError("Email can not be left empty");
            password.setError("Passwordcan not be left empty");
        } else if (TextUtils.isEmpty(fireemail)) {
            email.setError("Email can not be left empty");
        } else if (TextUtils.isEmpty(firepassword) ) {
            password.setError("Passwordcan not be left empty");
        }
        else {
            progressBar.setMessage("Login You in");
            progressBar.show();
            auth.signInWithEmailAndPassword(fireemail, firepassword)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.dismiss();
                            Toast.makeText(MainActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                    Intent intent=new Intent(MainActivity.this,Student.class);
                                    startActivity(intent);
                            }
                        }
                    });
        }
    }
}
