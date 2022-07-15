package com.cookandroid.kneelingbus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    //인스턴스 선언
    private FirebaseAuth mFirebaseAuth;//파이어베이스 인증
    private DatabaseReference mDatebaseRef;//실시간 DB
    private EditText mEtEmail, mEtPwd;//
    private Button mLogin, mRegister;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //
        mFirebaseAuth = FirebaseAuth.getInstance();//파이어베이스 초기화
        mDatebaseRef = FirebaseDatabase.getInstance().getReference("Kneelingbus");
        //
        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);

        mLogin = findViewById(R.id.btn_login);
        mRegister = findViewById(R.id.btn_getregister);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 요청

                //문자열타입변수에 레이아웃의 있는 위젯을 아이디로 식별한 상태인 변수를 넣어줌
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();

                //파이어베이스
                mFirebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       //로그인이 정상적으로 되면 task가 데이터를 보냄, 화면전환 후 파쇄
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this,"로그인 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        mRegister= findViewById(R.id.btn_register);
        mRegister.setOnClickListener(view -> {
            //로그인 화면에서 회원가입 화면으로 이동
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
