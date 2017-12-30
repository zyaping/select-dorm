package com.example.shirley.selectdorm.selectdormitory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shirley.selectdorm.R;

public class SelectType extends Activity implements View.OnClickListener{
    public static SelectType instance;
    private Button oneBtn, twoBtn, threeBtn, fourBtn;
    private ImageView stHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);
        instance = this;
        initView();
    }
    void initView() {
        oneBtn = (Button)findViewById(R.id.st_one_btn);
        twoBtn = (Button)findViewById(R.id.st_two_btn);
        threeBtn = (Button)findViewById(R.id.st_three_btn);
        fourBtn = (Button)findViewById(R.id.st_four_btn);
        stHome = (ImageView)findViewById(R.id.st_home);
        oneBtn.setOnClickListener(this);
        twoBtn.setOnClickListener(this);
        threeBtn.setOnClickListener(this);
        fourBtn.setOnClickListener(this);
        stHome.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        int number;
        switch (view.getId()) {
            case R.id.st_one_btn:
                number = 1;
                break;
            case R.id.st_two_btn:
                number = 2;
                break;
            case R.id.st_three_btn:
                number = 3;
                break;
            case R.id.st_four_btn:
                number = 4;
                break;
            case R.id.st_home:
                number = 0;
                break;
            default:
                number = -1;
                break;
        }
        if(number == -1) {

        } else if(number == 0) {
            Intent i = new Intent(SelectType.this, MainActivity.class);
            Intent geti = getIntent();
            i.putExtra("id", geti.getStringExtra("id"));
            startActivity(i);
            finish();
        } else {
            Intent geti = getIntent();
            Intent i = new Intent(SelectType.this, SelectDormitory.class);
            i.putExtra("type", number);
            i.putExtra("name", geti.getStringExtra("name"));
            i.putExtra("id", geti.getStringExtra("id"));
            i.putExtra("gender", geti.getStringExtra("gender"));
            i.putExtra("vcode", geti.getStringExtra("vcode"));
            startActivity(i);
        }
    }
}
