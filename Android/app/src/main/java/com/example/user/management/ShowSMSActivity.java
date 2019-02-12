package com.example.user.management;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by user on 2017-08-17.
 */

public class ShowSMSActivity extends Activity {

    Context mContext;
    TextView originNum, originText,smsDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showsms);

        smsDate = (TextView) findViewById(R.id.smsDate);
        originNum = (TextView) findViewById(R.id.originNum);
        originText = (TextView) findViewById(R.id.originText);
        Button falseButton = (Button) findViewById(R.id.falseButton);
        Button trueButton = (Button) findViewById(R.id.trueButton);

        Intent smsIntent = getIntent();

        final String originNumber = smsIntent.getStringExtra("originNum");
        String originDate = smsIntent.getStringExtra("smsDate");
        String originSmsText = smsIntent.getStringExtra("originText");

        originNum.setText(originNumber);
        smsDate.setText(originDate);
        originText.setText(originSmsText);

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowSMSActivity.this, FalseActivity.class);
                intent.putExtra("originNum", originNumber);
                ShowSMSActivity.this.startActivity(intent);
                finish();

            }
        });
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowSMSActivity.this, TrueActivity.class);
                intent.putExtra("originNum", originNumber);
                ShowSMSActivity.this.startActivity(intent);
                finish();

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }


}

