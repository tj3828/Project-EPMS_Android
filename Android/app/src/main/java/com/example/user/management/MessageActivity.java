package com.example.user.management;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by user on 2017-08-17.
 */

public class MessageActivity extends Activity{

    Context mContext;

    EditText smsNumber;
    int mHour, mMinute, mHour1, mMinute1;
    TextView smsTime2, smsTime3, smsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message1);

        mContext = this;
        smsTime2 = (TextView) findViewById(R.id.smsTime);
        smsTime3 = (TextView) findViewById(R.id.smsTime1);

        Calendar cal = new GregorianCalendar();
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);
        mHour1 = cal.get(Calendar.HOUR_OF_DAY);
        mMinute1 = cal.get(Calendar.MINUTE);

        UpdateNow();

        smsNumber = (EditText) findViewById(R.id.smsNumber);
        smsText = (TextView) findViewById(R.id.smsText);



        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        smsText.setText("\""+intent.getStringExtra("userID") +"\""+ "님에게 주차지역의 권한을 요청합니다." + "\n" + "불이행지수 : 0.0점");
        smsNumber.setText(intent.getStringExtra("userAge"));



    }

    // 이용시간을 선택할 수 있는 다이얼로그 출력
    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.timeButton:
                new TimePickerDialog(MessageActivity.this, mTimeSetListener, mHour, mMinute, false).show();
                break;
        }
        switch (v.getId()) {
            case R.id.imageButton:
                new TimePickerDialog(MessageActivity.this, mTimeSetListener1, mHour1, mMinute1, false).show();
                break;
        }
    }

        TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;

                UpdateNow();
            }
        };

        TimePickerDialog.OnTimeSetListener mTimeSetListener1 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour1 = hourOfDay;
                mMinute1 = minute;

                UpdateNow();
            }
        };

        void UpdateNow() {
        smsTime2.setText(String.format("%d:%d", mHour, mMinute));
        smsTime3.setText(String.format("%d:%d", mHour1, mMinute1));
    }


    // 문자메세지 전송버튼 누렀을 때
    public void sendSMS(View v){
        String smsNum = smsNumber.getText().toString();
        String smsText1 = smsText.getText().toString() + "\n" + "이용시간 : " + smsTime2.getText().toString() + "~" + smsTime3.getText().toString();


        if (smsNum.length()>0 && smsText1.length()>0){
            sendSMS(smsNum, smsText1);
        }else{
            Toast.makeText(this, "모두 입력해 주세요", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendSMS(String smsNum, String smsText1){
        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        /**
         * SMS가 발송될때 실행
         * When the SMS massage has been sent
         */
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        // 전송 성공
                        Toast.makeText(mContext, "요청하였습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        // 전송 실패
                        Toast.makeText(mContext, "전송 실패", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        // 서비스 지역 아님
                        Toast.makeText(mContext, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        // 무선 꺼짐
                        Toast.makeText(mContext, "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        // PDU 실패
                        Toast.makeText(mContext, "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        /**
         * SMS가 도착했을때 실행
         * When the SMS massage has been delivered
         */
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        // 도착 완료
                        Toast.makeText(mContext, "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        // 도착 안됨
                        Toast.makeText(mContext, "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));

        SmsManager mSmsManager = SmsManager.getDefault();
        mSmsManager.sendTextMessage(smsNum, null, smsText1, sentIntent, deliveredIntent);
        finish();
    }
}

