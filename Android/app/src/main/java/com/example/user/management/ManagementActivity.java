package com.example.user.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManagementActivity extends AppCompatActivity {
    String userID, userAge;

    private ListView listView;
    private UserListAdapter adapter;
    private List<User> userList;
    private List<User> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        // 유저 리스트를 담은 인텐트 획득
        Intent intent = getIntent();

        listView = (ListView) findViewById(R.id. listView);
        userList = new ArrayList<User>();
        saveList = new ArrayList<User>();
        adapter = new UserListAdapter(getApplicationContext(), userList, saveList);
        listView.setAdapter(adapter);

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            // 사용자의 아이디와 나이를 뽑아 리스트에 저장
            while (count < jsonArray.length())
            {
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                userAge = object.getString("userAge");
                User user = new User(userID, userAge);
                userList.add(user);
                saveList.add(user);
                count++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 검색에 따른 결과 목록 출력
        EditText search = (EditText) findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // 텍스트가 변화될 때마다 목록 갱신
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    //
    public void searchUser(String search) {
        userList.clear();
        for(int i = 0; i < saveList.size(); i++) {
            if(saveList.get(i).getUserID().contains(search))
            {
                userList.add(saveList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }


}
