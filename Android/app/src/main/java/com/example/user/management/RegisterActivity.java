package com.example.user.management;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.json.JSONObject;



public class RegisterActivity extends AppCompatActivity {

    EditText idText, passwordText, nameText, ageText, btnameText, addressText;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        idText =(EditText)findViewById(R.id.idText);
        passwordText =(EditText)findViewById(R.id.passwordText);
        nameText =(EditText)findViewById(R.id.nameText);
        ageText =(EditText)findViewById(R.id.ageText);
        btnameText = (EditText) findViewById(R.id.btnameText);
        addressText = (EditText) findViewById(R.id.addressText);


        // 회원가입 버튼 클릭시
        registerButton=(Button)findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID=idText.getText().toString();
                String userPassword=passwordText.getText().toString();
                String userName=nameText.getText().toString();
                String userAge=ageText.getText().toString();
                String userBTName=btnameText.getText().toString();
                String userAddress=addressText.getText().toString();

                Response.Listener<String> responseListener=new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            // 회원가입 요청의 반환값에 따른 처리
                            if(success){
                                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                                // 다이얼로그 확인 클릭 시 액티비티 전환
                                builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                                                RegisterActivity.this.startActivity(intent);
                                            }
                                        })
                                        .create()
                                        .show();

                            }else{
                                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("다시 시도", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent=new Intent(RegisterActivity.this, RegisterActivity.class);
                                                RegisterActivity.this.startActivity(intent);
                                            }
                                        })
                                        .create()
                                        .show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest registerRequest=new RegisterRequest(userID, userPassword,
                        userName, userAge, userBTName, userAddress, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);

                queue.add(registerRequest);
            }
        });
    }}
