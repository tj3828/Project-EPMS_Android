package com.example.user.management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by user on 2017-08-18.
 */

public class ShowtrueActivity extends Activity {


    TextView originNum, originText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_false);

        TextView smsDate = (TextView) findViewById(R.id.smsDate);
        originNum = (TextView) findViewById(R.id.originNum);
        originText = (TextView) findViewById(R.id.originText);

        Intent smsIntent = getIntent();

        final String originNumber = smsIntent.getStringExtra("originNum");
        String originDate = smsIntent.getStringExtra("smsDate");
        String originSmsText = smsIntent.getStringExtra("originText");

        originNum.setText(originNumber);
        smsDate.setText(originDate);
        originText.setText(originSmsText);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

}