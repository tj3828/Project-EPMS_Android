package com.example.user.management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import static com.example.user.management.R.id.checkBox1;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences appData;
    private boolean saveLoginData, loginChecked;
    private EditText idText = null;
    private EditText passwordText = null;
    private Button loginButton;
    private String id = null;
    private String pwd = null;
    private CheckBox checkBox, autoLogin;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appData = getSharedPreferences("appData", MODE_PRIVATE);

        load();

         idText = (EditText) findViewById(R. id. idText);
         passwordText = (EditText) findViewById(R. id. passwordText);
         loginButton = (Button) findViewById(R. id. loginbutton);
         checkBox = (CheckBox) findViewById(R.id.checkBox);
         autoLogin = (CheckBox) findViewById(checkBox1);
         final TextView registerButton = (TextView) findViewById(R. id. registerButton);



         // 로그인한 정보가 있다면 id와 pw를 자동으로 입력하고 자동 로그인 기능을 true 변환
        if (saveLoginData) {
            idText.setText(id);
            passwordText.setText(pwd);
            checkBox.setChecked(saveLoginData);
        }

        // 로그인 되었다면 자동으로 자동 로그인 기능을 true로 변환
        autoLogin.setChecked(loginChecked);

        // 자동 로그인이 체크되어있고 아이디와 되어있다면 자동 로그인 시작.
        if(autoLogin.isChecked() && idText.getText().toString().length() != 0 && passwordText.getText().toString().length() != 0 ) {

            final String userID = idText.getText().toString();
            final String userPassword = passwordText.getText().toString();

            // String에 대한 응답을 받을 때 실행되는 volley 기능
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                // 응답에 대해 자동으로 호출되는 함수
                @Override
                public void onResponse(String response) {
                    try {
                        // 서버로부터 받는 데이터 정보는 JSONObject이므로 객체 생성
                        JSONObject jsonResponse = new JSONObject(response);
                        // success 키값의 데이터에 따른 로그인 처리
                        boolean success = jsonResponse.getBoolean("success");
                        // success 값이 true 일 경우
                        if (success) {
                            // 아이디와 패스워드를 intent에 담아 Main액티비티로 전달
                            String userID = jsonResponse.getString("userID");
                            String userPassword = jsonResponse.getString("userPassword");

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("userID", userID);
                            intent.putExtra("userPassword", userPassword);
                            LoginActivity.this.startActivity(intent);

                        // false 일 경우 대화상자로 실패 문구 출력
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("로그인에 실패하였습니다.")
                                    .setNegativeButton("다시 시도", null)
                                    .create()
                                    .show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            };

            // 실제 Volley에서 일어나는 작업
            //LoginRequest 객체를 위에서 생성한 responseListner, id ,pw를 함께 전달하여 로그인 상태 체크
            LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
            //RequestQueue를 생성해서
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            //queue에 loginRequest을 저장
            queue.add(loginRequest);
        }

        // 회원가입 버튼 클릭시 회원가입 액티비티로 이동
         registerButton.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                                                   LoginActivity.this.startActivity(registerIntent);
                                               }


         });

        // 로그인 버튼 클릭시 자동 로그인과 같이 volley이용하여 로그인 체크
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                save();
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                String userID = jsonResponse.getString("userID");
                                String userPassword = jsonResponse.getString("userPassword");

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPassword", userPassword);
                                LoginActivity.this.startActivity(intent);


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }

        });
    }

    // 뒤로가기 클릭시 종료하기 대화상자 출력 구현 메소드(미구현)
    public void onBackPressed() {      // 뒤로가기 버튼 : 종료확인 메시지
        super.onBackPressed();
    }

    //
    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        editor = appData.edit();
        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putBoolean("AUTO_LOGIN_DATA", autoLogin.isChecked());
        editor.putString("ID", idText.getText().toString().trim());
        editor.putString("PWD", passwordText.getText().toString().trim());
        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        loginChecked = appData.getBoolean("AUTO_LOGIN_DATA", false) ;
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
    }

    /*
    private boolean loginValidation(String id, String password) {
        if(appData.getString("ID","").equals(id) && appData.getString("PWD","").equals(password)) {
                      // login success
                   return true;
                 } else if (appData.getString("ID","").equals(null)){
                    // sign in first
                  Toast.makeText(LoginActivity.this, "Please Sign in first", Toast.LENGTH_LONG).show();
                     return false;
                 } else {
                      // login failed
                        return false;
                   }
      }
      */

    @Override
    protected void onNewIntent(Intent intent) {
        load();
    }
}
