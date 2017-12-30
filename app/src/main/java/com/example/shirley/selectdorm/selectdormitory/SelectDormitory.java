package com.example.shirley.selectdorm.selectdormitory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shirley.selectdorm.R;
import com.example.shirley.selectdorm.bean.Remain;
import com.example.shirley.selectdorm.bean.SelectResult;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
public class SelectDormitory extends Activity implements View.OnClickListener{
    public static SelectDormitory instance;
    private static final int REMAIN = 10, RESULT = 20;
    private int number;
    private TextView titleTv;
    private TextView nameTv, stuidTv, genderTv;
    private TextView rm5Tv, rm8Tv, rm9Tv, rm13Tv, rm14Tv;
    private TextView targetTv;
    private ImageView st5Tv, st8Tv, st9Tv, st13Tv, st14Tv;
    private EditText[] idTv;
    private EditText[] codeTv;
    private Button start_btn;
    private ImageView sdHomeTv, sdRefreshTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dormitory);
        instance = this;
        initView();
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REMAIN:
                    roomSet((Remain)msg.obj);
                    break;
                case RESULT:
                    SelectResult selectResult = (SelectResult) msg.obj;
                    if(selectResult.getError_code().equals("0")) {
                        Intent i = new Intent(SelectDormitory.this, ResultActivity.class);
                        i.putExtra("result", "success");
                        i.putExtra("id", stuidTv.getText().toString());
                        startActivity(i);
                    } else {
                        Intent i = new Intent(SelectDormitory.this, ResultActivity.class);
                        i.putExtra("result", "fail");
                        i.putExtra("id", stuidTv.getText().toString());
                    }
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sd_5_select:
                st5Tv.setImageResource(R.drawable.select_yes);
                st8Tv.setImageResource(R.drawable.select_no);
                st9Tv.setImageResource(R.drawable.select_no);
                st13Tv.setImageResource(R.drawable.select_no);
                st14Tv.setImageResource(R.drawable.select_no);
                targetTv.setText("5号楼");
                break;
            case R.id.sd_8_select:
                st5Tv.setImageResource(R.drawable.select_no);
                st8Tv.setImageResource(R.drawable.select_yes);
                st9Tv.setImageResource(R.drawable.select_no);
                st13Tv.setImageResource(R.drawable.select_no);
                st14Tv.setImageResource(R.drawable.select_no);
                targetTv.setText("8号楼");
                break;
            case R.id.sd_9_select:
                st5Tv.setImageResource(R.drawable.select_no);
                st8Tv.setImageResource(R.drawable.select_no);
                st9Tv.setImageResource(R.drawable.select_yes);
                st13Tv.setImageResource(R.drawable.select_no);
                st14Tv.setImageResource(R.drawable.select_no);
                targetTv.setText("9号楼");
                break;
            case R.id.sd_13_select:
                st5Tv.setImageResource(R.drawable.select_no);
                st8Tv.setImageResource(R.drawable.select_no);
                st9Tv.setImageResource(R.drawable.select_no);
                st13Tv.setImageResource(R.drawable.select_yes);
                st14Tv.setImageResource(R.drawable.select_no);
                targetTv.setText("13号楼");
                break;
            case R.id.sd_14_select:
                st5Tv.setImageResource(R.drawable.select_no);
                st8Tv.setImageResource(R.drawable.select_no);
                st9Tv.setImageResource(R.drawable.select_no);
                st13Tv.setImageResource(R.drawable.select_no);
                st14Tv.setImageResource(R.drawable.select_yes);
                targetTv.setText("14号楼");
                break;
            case R.id.re_home:
                Intent i = new Intent(SelectDormitory.this, MainActivity.class);
                Intent geti = getIntent();
                i.putExtra("id", geti.getStringExtra("id"));
                startActivity(i);
                SelectType.instance.finish();
                finish();
                break;
            case R.id.sd_refresh:
                refreshRoom(genderTv.getText().toString());
                Toast.makeText(SelectDormitory.this, "剩余床位信息已更新", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sd_start:
                selectRoom();
                break;
            default:
                break;
        }
    }
    void initView() {
        titleTv = (TextView)findViewById(R.id.re_title);
        nameTv = (TextView)findViewById(R.id.sd_per_name);
        stuidTv = (TextView)findViewById(R.id.sd_per_id);
        genderTv = (TextView)findViewById(R.id.sd_per_gender);
        rm5Tv = (TextView)findViewById(R.id.sd_5_remaining);
        rm8Tv = (TextView)findViewById(R.id.sd_8_remaining);
        rm9Tv = (TextView)findViewById(R.id.sd_9_remaining);
        rm13Tv = (TextView)findViewById(R.id.sd_13_remaining);
        rm14Tv = (TextView)findViewById(R.id.sd_14_remaining);
        targetTv = (TextView)findViewById(R.id.sd_target);
        idTv = new EditText[4];
        idTv[1] = (EditText)findViewById(R.id.sd_one_id);
        idTv[2] = (EditText)findViewById(R.id.sd_two_id);
        idTv[3] = (EditText)findViewById(R.id.sd_three_id);
        codeTv = new EditText[4];
        codeTv[1] = (EditText)findViewById(R.id.sd_one_vcode);
        codeTv[2] = (EditText)findViewById(R.id.sd_two_vcode);
        codeTv[3] = (EditText)findViewById(R.id.sd_three_vcode);
        st5Tv = (ImageView)findViewById(R.id.sd_5_select);
        st5Tv.setOnClickListener(this);
        st8Tv = (ImageView)findViewById(R.id.sd_8_select);
        st8Tv.setOnClickListener(this);
        st9Tv = (ImageView)findViewById(R.id.sd_9_select);
        st9Tv.setOnClickListener(this);
        st13Tv = (ImageView)findViewById(R.id.sd_13_select);
        st13Tv.setOnClickListener(this);
        st14Tv = (ImageView)findViewById(R.id.sd_14_select);
        st14Tv.setOnClickListener(this);
        start_btn = (Button)findViewById(R.id.sd_start);
        start_btn.setOnClickListener(this);
        sdHomeTv = (ImageView)findViewById(R.id.re_home);
        sdHomeTv.setOnClickListener(this);
        sdRefreshTv = (ImageView)findViewById(R.id.sd_refresh);
        sdRefreshTv.setOnClickListener(this);
        Intent i = getIntent();
        nameTv.setText(i.getStringExtra("name"));
        stuidTv.setText(i.getStringExtra("id"));
        genderTv.setText(i.getStringExtra("gender"));
        number = i.getIntExtra("type", 1);
        refreshUi(number);
        refreshRoom(genderTv.getText().toString());
    }
    void refreshUi(int type) {
        if(type == 1) {
            titleTv.setText("单人办理");
            findViewById(R.id.sd_roommate_info_title).setVisibility(View.INVISIBLE);
            findViewById(R.id.sd_roommate_info_layout).setVisibility(View.GONE);
        } else if(type == 2) {
            findViewById(R.id.sd_three_roommate_layout).setVisibility(View.GONE);
            findViewById(R.id.sd_two_roommate_layout).setVisibility(View.GONE);
            titleTv.setText("双人办理");
        } else if(type == 3) {
            findViewById(R.id.sd_three_roommate_layout).setVisibility(View.GONE);
            titleTv.setText("三人办理");
        } else {
            titleTv.setText("四人办理");
        }

    }
    public void refreshRoom(final String gender) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection con = null;
                Remain remain = null;
                try {
                    URL url = new URL("https://api.mysspku.com/index.php/V1/MobileCourse/getRoom?" + "gender=" + gender);
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
                    remain = parseJsonToRemain(response);
                    Message msg = new Message();
                    msg.what = REMAIN;
                    msg.obj = remain;
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
    Remain parseJsonToRemain(StringBuilder responseStr) {
        Gson gson = new Gson();
        Remain remain = gson.fromJson(responseStr.toString(), Remain.class);
        return remain;
    }
    SelectResult parseJsonToResult(StringBuilder responseStr) {
        Gson gson = new Gson();
        SelectResult selectResult = gson.fromJson(responseStr.toString(), SelectResult.class);
        return selectResult;
    }
    void roomSet(Remain remain) {
        rm5Tv.setText(remain.getData().getM5());
        rm8Tv.setText(remain.getData().getM8());
        rm9Tv.setText(remain.getData().getM9());
        rm13Tv.setText(remain.getData().getM13());
        rm14Tv.setText(remain.getData().getM14());
    }
    public void selectRoom() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection con = null;
                SelectResult selectResult;
                try {
                    URL url = new URL("https://api.mysspku.com/index.php/V1/MobileCourse/SelectRoom");
                    AllowX509TrustManager.allowAllSSL();
                    con = (HttpsURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setConnectTimeout(8000);
                    StringBuilder datasb = new StringBuilder();
                    datasb.append("num=" + number + "&stuid=" + stuidTv.getText().toString());
                    for(int i =1 ; i < number; i++) {
                        datasb.append("&stu" + i + "id=" + idTv[i].getText().toString());
                        datasb.append("&v" + i + "code=" + codeTv[i].getText().toString());
                    }
                    datasb.append("&buildingNo=" + targetTv.getText().toString().charAt(0));
                    Log.d("qwer", datasb.toString());
                    String data = datasb.toString();
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    con.setRequestProperty("Content-Length", Integer.toString(data.length()));
                    con.setDoOutput(true);
                    OutputStream out = con.getOutputStream();
                    out.write(data.getBytes());

                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while((str = reader.readLine()) != null) {
                        response.append(str);
                    }
                    Log.d("qwer", response.toString());
                    selectResult = parseJsonToResult(response);
                    Log.d("qwer", selectResult.toString());
                    Message msg = new Message();
                    msg.what = RESULT;
                    msg.obj = selectResult;
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