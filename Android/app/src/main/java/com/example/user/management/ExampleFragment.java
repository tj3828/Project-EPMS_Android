/*
 * Copyright (C) 2014 Bluetooth Connection Template
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.user.management;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class ExampleFragment extends Fragment {

	private Context mContext = null;
	private IFragmentListener mFragmentListener = null;
	private Handler mActivityHandler = null;

    Button btnON, btnOFF,btnCancel,btnSetting;
	Timer timer = null;
	TimerTask timerTask = null;
    Message message = null;



	@SuppressLint("ValidFragment")
	public ExampleFragment(Context c, IFragmentListener l, Handler h) {
		mContext = c;
		mFragmentListener = l;
		mActivityHandler = h;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);

		timerTask = new TimerTask() {
			@Override
			public void run() {
				String message = "a";
				if (message != null && message.length() > 0)
					sendMessage(message);
			}

	};
	timer = new Timer();
		timer.schedule(timerTask, 0, 1000);


		btnON = (Button) rootView.findViewById(R.id.btnOnButton);
        btnOFF = (Button) rootView.findViewById(R.id.btnOFFButton);
		btnCancel = (Button) rootView.findViewById(R.id.CancelButton);
		btnSetting = (Button) rootView.findViewById(R.id.SettingButton);

		btnON.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
            String message = "o";
				if(message != null && message.length() > 0)
					sendMessage(message);  //아래 함수에 message를 전달하여 호출
			}
		});
		btnOFF.setOnClickListener(new View.OnClickListener()
		{
		    @Override
		    public void onClick(View v) {
			String message = "x";
			if(message != null && message.length() > 0)

				sendMessage(message);  //아래 함수에 message를 전달하여 호출
		}
	});
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				btnCancel.setEnabled(true);
				if(timer == null && timerTask == null){
				TimerStart();}
			}


		});

		btnSetting.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
					btnON.setEnabled(true);
					btnOFF.setEnabled(true
					);
					btnCancel.setEnabled(false);
					TimerStop();

		}});

		btnCancel.setOnLongClickListener(new View.OnLongClickListener() {     //롱클릭시
			public boolean onLongClick(View v) {
				btnSetting.setEnabled(true);
				btnCancel.setEnabled(false);
				btnON.setEnabled(false);
				btnOFF.setEnabled(false);
				return false;
			}
		});


		btnSetting.setOnLongClickListener(new View.OnLongClickListener() {     //롱클릭시
			public boolean onLongClick(View v) {
				btnSetting.setEnabled(false);
				btnCancel.setEnabled(true);
				btnON.setEnabled(false);
				btnOFF.setEnabled(false);
				return false;
			}
		});



		return rootView;
	}




	public void TimerStart(){

		 timerTask = new TimerTask() {

			@Override
			public void run() {
				String message = "a";
				if (message != null && message.length() > 0)
					sendMessage(message);
			}
		};
		timer = new Timer();
		timer.schedule(timerTask, 0, 1000);

	}

	public void TimerStop() {
		if(timerTask !=null) {
			timerTask.cancel();
			timerTask=null;
		}

		if(timer !=null) {
			timer.cancel();
			timer.purge();
			timer=null;
		}

	}


	public void sendMessage(String message) {
		if(message == null || message.length() < 1)

			return;

		// send to remote
		if(mFragmentListener != null)
			mFragmentListener.OnFragmentCallback(IFragmentListener.CALLBACK_SEND_MESSAGE, 0, 0, message, null,null);

		else

			return;

	}






}







