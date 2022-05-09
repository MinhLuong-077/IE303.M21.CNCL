package com.example.ewallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ewallet.utils.DAOUsers;
import com.example.ewallet.utils.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout full_name, email, phone, password, repassword;
    private Button register, switch_login;
    private FirebaseAuth auth;
    private ImageView img;
    private TextView greeting, slogan;
    private Number init_balance=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        full_name = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        repassword = findViewById(R.id.repassword);
        switch_login = findViewById(R.id.switchlogin);
        auth = FirebaseAuth.getInstance();

        //hooks
        img = findViewById(R.id.logo_img);
        greeting = findViewById(R.id.greeting);
        slogan = findViewById(R.id.slogan);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String txt_email = email.getEditText().getText().toString();
                String txt_password = password.getEditText().getText().toString();
                String txt_repassword = repassword.getEditText().getText().toString();
                String txt_full_name = full_name.getEditText().getText().toString();
                String txt_phone = phone.getEditText().getText().toString();

                if (TextUtils.isEmpty(txt_full_name)){
                    full_name.getEditText().setError("Full name is required!");
                    full_name.getEditText().requestFocus();
                    full_name.getEditText().setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            full_name.getEditText().setError(null);
                            full_name.getEditText().clearFocus();
                            return false;
                        }
                    });
                }
                else if (TextUtils.isEmpty(txt_email)){
                    email.getEditText().setError("Email is required!");
                    email.getEditText().requestFocus();
                    email.getEditText().setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            email.getEditText().setError(null);
                            email.getEditText().clearFocus();
                            return false;
                        }
                    });
                }
                else if (TextUtils.isEmpty(txt_phone)){
                    phone.getEditText().setError("Phone is required!");
                    phone.getEditText().requestFocus();
                    phone.getEditText().setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            phone.getEditText().setError(null);
                            phone.getEditText().clearFocus();
                            return false;
                        }
                    });
                }
                else if (TextUtils.isEmpty(txt_password)){
                    password.setEndIconVisible(false);
                    password.getEditText().setError("Password is required!");
                    password.getEditText().requestFocus();
                    password.getEditText().setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            password.setEndIconVisible(true);
                            password.getEditText().setError(null);
                            password.getEditText().clearFocus();
                            return false;
                        }
                    });
                }
                else if (TextUtils.isEmpty(txt_repassword)){
                    repassword.setEndIconVisible(false);
                    repassword.getEditText().setError("Repassword is required!");
                    repassword.getEditText().requestFocus();
                    repassword.getEditText().setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            repassword.setEndIconVisible(true);
                            repassword.getEditText().setError(null);
                            repassword.getEditText().clearFocus();
                            return false;
                        }
                    });
                }
                else if (txt_password.length()<6){
                    password.getEditText().setError("Password is too short!");
                    password.getEditText().requestFocus();
                    password.getEditText().setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            password.getEditText().setError(null);
                            password.getEditText().clearFocus();
                            return false;
                        }
                    });
                }
                else if (!txt_password.equals(txt_repassword)){
                    password.setEndIconVisible(false);
                    password.getEditText().setError("Password and Repassword are not match");
                    password.getEditText().requestFocus();
                    repassword.setEndIconVisible(false);
                    repassword.getEditText().setError("Password and Repassword are not match");
                    repassword.getEditText().requestFocus();
                    full_name.getEditText().setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            password.setEndIconVisible(true);
                            password.getEditText().setError(null);
                            password.getEditText().clearFocus();
                            repassword.setEndIconVisible(true);
                            repassword.getEditText().setError(null);
                            repassword.getEditText().clearFocus();
                            return false;
                        }
                    });
                } else {
                    registerUser(txt_email, txt_password, init_balance, txt_phone, txt_full_name);
                }
            }
        });

        switch_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(img, "logo_img");
                pairs[1] = new Pair<View, String>(greeting, "greeting");
                pairs[2] = new Pair<View, String>(slogan, "slogan");
                pairs[3] = new Pair<View, String>(email, "email");
                pairs[4] = new Pair<View, String>(password, "password");
                pairs[5] = new Pair<View, String>(register, "login");
                pairs[6] = new Pair<View, String>(switch_login, "btn_tran");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }

    private void writeDatabase(String full_name, String email, String phone, Number balance) {
        DAOUsers dao = new DAOUsers();
        Users users = new Users(full_name, email, phone, balance);
        dao.add(users);
    }

    private void registerUser(String email, String password, Number balance, String phone, String full_name) {
        Integer flag = 0;
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registration user successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    writeDatabase(full_name, email, phone, balance);
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
