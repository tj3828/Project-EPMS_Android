package com.example.user.management;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 2017-08-01.
 *
 *  리스트뷰에 사용자 목록을 띄우고 요청하기 버튼을 누르면 해당 사용자에게 전송할 수 있는 메세지 창으로 이동
 */

public class UserListAdapter extends BaseAdapter {



    private Context context;
    private List<User> userList;
    private List<User> saveList;

    public UserListAdapter(Context context, List<User> userList, List<User> saveList) {
        this.context = context;
        this.userList = userList;
        this.saveList = saveList;
    }


    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.user, null);
        final TextView userID = (TextView) v.findViewById(R.id.userID);
        TextView userAge = (TextView) v.findViewById(R.id.userAge);
        Button sendButton = (Button) v.findViewById(R.id.sendButton);

        userID.setText(userList.get(i).getUserID());
        userAge.setText(userList.get(i).getUserAge());
        userAge.setVisibility(View.GONE);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(),MessageActivity.class);
                intent.putExtra("userID", userList.get(i).getUserID());
                intent.putExtra("userAge", userList.get(i).getUserAge());
                context.startActivity(intent);
            }
        });


        v.setTag(userList.get(i).getUserID());

        return v;
    }

}




