package com.example.shirley.selectdorm.selectdormitory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shirley.selectdorm.R;
import com.example.shirley.selectdorm.bean.PerInfo;
import com.example.shirley.selectdorm.bean.Result;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends Activity implements View.OnClickListener {
    private static final int NOT_EXIST = 40001, PASSWORD_ERR = 40002, PARAMETER_ERR = 40009, LOGIN_SUCCESS = 0;
    private EditText mStudentID, mPassword;
    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.login_button);
        loginBtn.setOnClickListener(this);

        initView();
    }

    void initView() {

        mStudentID = (EditText) findViewById(R.id.login_username);
        mPassword = (EditText) findViewById(R.id.login_password);

    }
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.login_button:
                loginGet(mStudentID.getText().toString(), mPassword.getText().toString());
                break;
            default:
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case NOT_EXIST:
                case PARAMETER_ERR:
                    Toast.makeText(LoginActivity.this, "学号或密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                case PASSWORD_ERR:
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    break;
                case LOGIN_SUCCESS:
                    Toast.makeText(LoginActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                this.sleep(1500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.putExtra("id", mStudentID.getText().toString());
                            startActivity(i);
                            finish();

                        }
                    }.start();
                    break;
                default:
                    break;
            }
        }
    };

    public void loginGet(final String username, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection con = null;
                try {
                    URL url = new URL("https://api.mysspku.com/index.php/V1/MobileCourse/Login?username="
                            + username + "&password=" + password);
                    AllowX509TrustManager.allowAllSSL();
                    con = (HttpsURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while((str = reader.readLine()) != null) {
                        response.append(str);
                    }
                    Gson mGson = new Gson();
                    Result result = mGson.fromJson(response.toString(), Result.class);
                    Message msg = new Message();
                    if(result.getErrcode().equals("0")) {
                        URL url2 = new URL("https://api.mysspku.com/index.php/V1/MobileCourse/getDetail" +
                                "?" + "stuid=" + username);
                        AllowX509TrustManager.allowAllSSL();
                        con = (HttpsURLConnection) url2.openConnection();
                        con.setRequestMethod("GET");
                        con.setConnectTimeout(8000);
                        InputStream in2 = con.getInputStream();
                        BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
                        StringBuilder response2 = new StringBuilder();
                        String str2;
                        while((str2 = reader2.readLine()) != null) {
                            response2.append(str2);
                        }
                        Gson mGson2 = new Gson();
                        final PerInfo perInfo = mGson2.fromJson(response2.toString(), PerInfo.class);
                        if(perInfo.getErrcode().equals("0")) {
                            msg.what = Integer.valueOf(perInfo.getErrcode());
                            msg.obj = "登录成功";

                        } else {
                            msg.what = Integer.valueOf(perInfo.getErrcode());
                            msg.obj = "学号不存在";
                        }
                    } else {
                        msg.what = Integer.valueOf(result.getErrcode());
                        msg.obj = result.getData().getErrmsg();
                    }
                    handler.sendMessage(msg);
                } catch(Exception e) {
                    e.printStackTrace();
                } finally {
                    if(con != null) {
                        con.disconnect();
                    }
                }
            }
        }).start();
    }
}
