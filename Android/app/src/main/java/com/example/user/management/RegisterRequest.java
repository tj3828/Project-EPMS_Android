package com.example.user.management;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017-08-04.
 */

public class RegisterRequest extends StringRequest {

    final static private String URL="http://tj3828.cafe24.com/Register.php";

    private Map<String, String> parameters;

/*    public RegisterRequest(int method, String url,
                           Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }*/

    public RegisterRequest(String userID, String userPassword, String userName, String userAge,
                           String userBTName, String userAddress, Response.Listener<String> listener){

        super(Method.POST, URL, listener, null);
        try {
            //한글 깨짐 방지
            //  String st=URLEncoder.encode(userName, "UTF-8");

            parameters=new HashMap<>();
            parameters.put("userID", userID);
            parameters.put("userPassword", userPassword);
            parameters.put("userName", userName);
            parameters.put("userAge", userAge +"");
            parameters.put("userBTName", userBTName);
            parameters.put("userAddress", userAddress);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected Map<String, String> getParams() {
        return parameters;
    }


}
