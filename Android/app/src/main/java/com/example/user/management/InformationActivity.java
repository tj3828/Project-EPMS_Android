package com.example.user.management;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2017-08-15.
 */


public class InformationActivity extends ActionBarActivity {



    // 데이터를 보기위한 TextView
    TextView IdText, userText, BTNameText, AddressText, AgeText, pointText;

    // PHP를 읽어올때 사용할 변수
    public GettingPHP gPHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        gPHP = new GettingPHP();
        IdText = (TextView)findViewById(R.id.IdText);
        userText = (TextView)findViewById(R.id.userText);
        BTNameText = (TextView)findViewById(R.id.BTNameText);
        AddressText = (TextView)findViewById(R.id.AddressText);
        AgeText = (TextView)findViewById(R.id.AgeText);
        pointText = (TextView)findViewById(R.id.pointText);
        String message1 = "\"" + "100 point" + "\"" ;
        pointText.setText(message1);

        Intent intent = getIntent();
        String userID1 = intent.getStringExtra("userID");
        String userPassword1 = intent.getStringExtra("userPassword");
        // 데이터를 받아올 PHP 주소
        String url = "http://tj3828.cafe24.com/Information.php?userID=" + userID1 + "&userPassword=" + userPassword1;
        gPHP.execute(url);
    }


    class GettingPHP extends AsyncTask<String, Integer, String> {
        String target;

        @Override
        protected void onPreExecute() {
            Intent intent = getIntent();
            String userID1 = intent.getStringExtra("userID");
            String userPassword1 = intent.getStringExtra("userPassword");
            target = "http://tj3828.cafe24.com/Information.php?userID=" + userID1 + "&userPassword=" + userPassword1;
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            try {


                URL phpUrl = new URL(target);
                HttpURLConnection conn = (HttpURLConnection)phpUrl.openConnection();
                if ( conn != null ) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ( true ) {
                            String line = br.readLine();
                            if ( line == null )
                                break;
                            jsonHtml.append(line + "\n");
                        }

                        br.close();
                    }
                    conn.disconnect();

                }

            } catch ( Exception e ) {
                e.printStackTrace();

            }
            return jsonHtml.toString();
        }

        protected void onPostExecute(String str) {
            try {

                // PHP에서 받아온 JSON 데이터를 JSON오브젝트로 변환
                JSONObject jObject = new JSONObject(str);

                // results라는 key는 JSON배열로 되어있다.
                JSONArray results = jObject.getJSONArray("results");


                String zz= "";
                String zz1= "";
                String zz2= "";
                String zz3= "";
                String zz4= "";

                for ( int i = 0; i < results.length(); ++i ) {
                    JSONObject temp = results.getJSONObject(i);
                    zz += "\""+temp.get("userID")+"\"";
                    zz1 += "\""+temp.get("userName") + "님"+"\"";
                    zz2 +="\""+temp.get("userBTName")+"\"";
                    zz3 +="\""+temp.get("userAddress")+"\"";
                    zz4 += "\"" + temp.get("userAge") + "\"";
                }
                IdText.setText(zz);
                userText.setText(zz1);
                BTNameText.setText(zz2);
                AddressText.setText(zz3);
                AgeText.setText(zz4);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}
