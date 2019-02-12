package com.example.user.management;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        TextView welcomeMessage = (TextView) findViewById(R. id. welcomeMessage);
        TextView idMessage = (TextView) findViewById(R. id. idMessage);
        Button managementButton = (Button) findViewById(R.id.managementButton);
        Button manualButton = (Button)findViewById(R.id.manualButton);
        Button informationButton = (Button) findViewById(R.id.informationButton);
        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        Button messageButton = (Button) findViewById(R.id.messageButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SharedPreferences에 저장된 값들을 로그아웃 버튼을 누르면 삭제하기 위해
                //SharedPreferences를 불러옵니다. 메인에서 만든 이름으로
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                SharedPreferences appData = getSharedPreferences("appData", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = appData.edit();
                // 로그아웃시 자동로그인 기능 해제
                editor.putBoolean("AUTO_LOGIN_DATA",false);
        //      editor.clear();
                editor.commit();
                Toast.makeText(MainActivity.this, "로그아웃", Toast.LENGTH_SHORT).show();
                finish();
            }
        });






        String message = "환영합니다.";
        welcomeMessage.setText(message);
        // 로그인 액티비티에서 넘어온 계정정보 인텐트를 이용해 출력
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String userPassword = intent.getStringExtra("userPassword");
        String message1 = userID + "님!";
        idMessage.setText(message1);

        // 수동 조작 액티비티로 이동
        manualButton.setOnClickListener(new Button.OnClickListener() {
@Override
              public void onClick(View v) {
                  Intent intent = new Intent(MainActivity.this, ManualActivity.class);
                  startActivity(intent);

                                            }
                                        });
        // 내정보 액티비티로 이동
        informationButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPassword", userPassword);
                startActivity(intent);

            }
        });

        // 주차지역검색 / 요청하기 액티비티로 이동
        managementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new  BackgroundTask() .execute();

            }
        });

        // 메시지함으로 이동
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setType("vnd.android-dir/mms-sms");
                startActivity(intent);

            }
        });


    }


    // AsynsTask = Thread + Handler 역할
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

                // 백그라운드 작업이 실행되기 전에 호출
                @Override
                protected void onPreExecute() {
                    target = "http://tj3828.cafe24.com/List.php";
                }

                // 실제 백그라운드에서 일어나는 동작
               @Override
                 protected String doInBackground(Void... voids) {
                    try {
                        // 해당 URL에서 읽어온 데이터 값을 StringBuilder에 저장
                        URL url = new URL(target);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String temp;
                        StringBuilder stringBuilder = new StringBuilder();
                        while((temp = bufferedReader.readLine())  !=null)
                        {
                            stringBuilder.append(temp + "Wn" );
                        }
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                        return  stringBuilder.toString().trim();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public void onProgressUpdate(Void...values) {
                    super .onProgressUpdate(values);
                }

                // 백그라운드 작업이 완료된 후 결과
                // doIngBackground에서 결과값을 intent에 저장
                @Override
                public void onPostExecute(String result) {
                    Intent intent = new Intent(MainActivity.this, ManagementActivity.class);
                    intent.putExtra("userList", result);
                    MainActivity.this.startActivity(intent);

                }

    }
    public void onBackPressed() {      // 뒤로가기 버튼 : 종료확인 메시지
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("정말 종료하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
                am.restartPackage(getPackageName());
                android.os.Process.killProcess(android.os.Process.myPid());
                // 프로세스 및 스레드 죽이기.
                finish();
            }
        });
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


}



