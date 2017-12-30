package com.example.shirley.selectdorm.selectdormitory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shirley.selectdorm.R;
import com.example.shirley.selectdorm.bean.PerInfo;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends Activity implements View.OnClickListener{
    public static MainActivity instance;
    private final int UPDATE = 123, GETINFO = 456;
    private TextView nameTv, studentIdTv, genderTv, checkCodeTv, selectedRoomTv;
    private Button startBtnTv;
    private ImageView perInfoRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        //设置监听器
        perInfoRefresh = (ImageView)findViewById(R.id.main_refresh);
        perInfoRefresh.setOnClickListener(this);
        startBtnTv = (Button)findViewById(R.id.main_start_btn);
        startBtnTv.setOnClickListener(this);

        initView();
    }
    void initView() {
        nameTv = (TextView)findViewById(R.id.main_name);
        studentIdTv = (TextView)findViewById(R.id.main_stuid);
        genderTv = (TextView)findViewById(R.id.main_gender);
        checkCodeTv = (TextView)findViewById(R.id.main_vcode);
        selectedRoomTv = (TextView)findViewById(R.id.main_room);

        Intent i = getIntent();
        infoGet(i.getStringExtra("id"));
    }
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.main_start_btn:
                Intent i = new Intent(this, SelectType.class);
                i.putExtra("name", nameTv.getText().toString());
                i.putExtra("id", studentIdTv.getText().toString());
                i.putExtra("gender", genderTv.getText().toString());
                i.putExtra("vcode", checkCodeTv.getText().toString());
                startActivity(i);
                break;
            case R.id.main_refresh:
                infoGet(studentIdTv.getText().toString());
                Toast.makeText(MainActivity.this, "个人信息已更新", Toast.LENGTH_SHORT).show();
//                Message msg = new Message();
//                msg.what = UPDATE;
//                handler.sendMessage(msg);
                break;
        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GETINFO:
                    PerInfo perInfo = (PerInfo) msg.obj;
                    update(perInfo);
                    break;
//                case UPDATE:
//                    Toast.makeText(MainActivity.this, "个人信息已更新", Toast.LENGTH_SHORT).show();
//                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
    public void update(PerInfo perInfo) {
        nameTv.setText(perInfo.getData().getName());
        studentIdTv.setText(perInfo.getData().getStudentid());
        genderTv.setText(perInfo.getData().getGender());
        checkCodeTv.setText(perInfo.getData().getVcode());
//                    selectedRoomTv.setText("234");
        if(perInfo.getData().getBuilding()== null) {
            selectedRoomTv.setText("未选宿舍");
        } else {
            selectedRoomTv.setText(perInfo.getData().getBuilding() + "-" + perInfo.getData().getRoom());
        }
    }
    public void infoGet(final String stuid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection con = null;
                PerInfo perInfo = null;
                try {
                    URL url = new URL("https://api.mysspku.com/index.php/V1/MobileCourse/getDetail" +
                            "?" + "stuid=" + stuid);
                    Log.d("abc", stuid);
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
                    perInfo = parseJson(response);
                    Log.d("def", perInfo.toString());

                    Message msg = new Message();
                    msg.what = GETINFO;
                    msg.obj = perInfo;
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
    PerInfo parseJson(StringBuilder responseStr) {
        Gson gson = new Gson();
        PerInfo pinfo = gson.fromJson(responseStr.toString(), PerInfo.class);
        return pinfo;
    }
}
