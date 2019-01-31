package com.newsapp.newsinbites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText emailText,passwordText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.emailTextLogin);
        passwordText = findViewById(R.id.passTextLogin);
        mAuth = FirebaseAuth.getInstance();
    }

    public void doLogin(View view) {
        final ProgressDialog p = new ProgressDialog(this);
        p.setMessage("Please Wait while we are logging you in");
        p.setTitle("Hold On ... ");
        p.setCancelable(false);
        p.setCanceledOnTouchOutside(false);
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Fields Cannot be left blank", Toast.LENGTH_SHORT).show();
        }else{
            p.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    p.dismiss();
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,AddNewsActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void doForgotPassword(View view) {
        startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this,AddNewsActivity.class));
            finish();
        }
    }

    public void goToRegisterActivity(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
}
