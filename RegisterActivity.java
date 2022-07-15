package com.cookandroid.kneelingbus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    private FirebaseAuth mFirebaseAuth;//파이어베이스 인증
    private DatabaseReference mDatabaseReference;//실시간 DB
    private EditText mEtEmail, mEtPwd;//회원가입 화면에 입력하는 필드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();//파이어베이스 초기화
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Kneelingbus"); //


        mEtEmail = findViewById(R.id.et_email);//액티비티레지스터 (레이아웃) 안 위젯의 아이디
        mEtPwd = findViewById(R.id.et_pwd);
        //회원가입 완료 버튼
        Button mBtnRegister = findViewById(R.id.btn_register);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입이시작, btn_register라는 아이디를 가진 버튼을 누르면
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                //문자열 타입의 변수를 지정하여 넣어줌 strEmail < mEtEmail < et_email .

                //파이어베이스어스 진행
                //파이어베이스에서 유저를 생성, (PassWord이용해 생성)
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    //인증처리가 완료되었을 때 처리 (위치 RegisterActivity)
                    //<> :
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){ // 정상정으로 값을 가져왔으면 (task가)실제로 회원가입 처리하고 결과값을 줌
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();//mFirebaseAuth 이미 선언해준 객체
                        //현재 회원가입이 완료된 유저를 가져올 수 있게 됨
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail()); // 파이어베이스 유저 객체로 부터 가져옴
                            account.setPassword(strPwd);//사용자가 입력한 것

                            //setValue ; 데이터베이스의 insert (데이터 삽입)행위, setValue에 UserAccount 삽입, child는 하위개념
                            mDatabaseReference.child("UserAccount").child(firebaseUser.getUid()).setValue(account);


                            Toast.makeText(RegisterActivity.this,"회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegisterActivity.this,"회원가입에 실패하셨습니다",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
       });


    }
}
