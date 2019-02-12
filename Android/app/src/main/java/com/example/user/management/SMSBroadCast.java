package com.example.user.management;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by user on 2017-08-17.
 */

public class SMSBroadCast extends BroadcastReceiver {
    String originDate, origNumber, Message;

    @Override
    public void onReceive(Context mContext, Intent intent) {
        String action = intent.getAction();

        if ("android.provider.Telephony.SMS_RECEIVED".equals(action)) {
            /**
             * SMS메세지 파싱
             */
            Bundle bundle = intent.getExtras();
            Object messages[] = (Object[]) bundle.get("pdus");
            SmsMessage smsMessage[] = new SmsMessage[messages.length];

            for (int i = 0; i < messages.length; i++) {
                /**
                 * PDU포멧의 SMS를 변환합니다
                 */
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
            }

            /**
             * 날짜 형식을 우리나라에 맞도록 변환합니다
             */
            Date curDate = new Date(smsMessage[0].getTimestampMillis());
            SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초", Locale.KOREA);

            originDate = mDateFormat.format(curDate);
            origNumber = smsMessage[0].getOriginatingAddress();
            Message = smsMessage[0].getMessageBody().toString();


            if (Message.equals("죄송합니다. 요청이 거절되었습니다.")) {
                Intent showSMSIntent = new Intent(mContext, ShowfalseActivity.class);
                showSMSIntent.putExtra("originNum", origNumber);
                showSMSIntent.putExtra("smsDate", originDate);
                showSMSIntent.putExtra("originText", Message);
                showSMSIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(showSMSIntent);
            }
            else if (Message.contains("요청에 승인하셨습니다. 비밀번호는"))
            {
                Intent showSMSIntent = new Intent(mContext, ShowtrueActivity.class);
                showSMSIntent.putExtra("originNum", origNumber);
                showSMSIntent.putExtra("smsDate", originDate);
                showSMSIntent.putExtra("originText", Message);
                showSMSIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(showSMSIntent);
            }
            else if(Message.contains("님에게 주차지역의 권한을 요청합니다."))
            {
                Intent showSMSIntent = new Intent(mContext, ShowSMSActivity.class);
                showSMSIntent.putExtra("originNum", origNumber);
                showSMSIntent.putExtra("smsDate", originDate);
                showSMSIntent.putExtra("originText", Message);
                showSMSIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(showSMSIntent);
            }
        }
    }
}