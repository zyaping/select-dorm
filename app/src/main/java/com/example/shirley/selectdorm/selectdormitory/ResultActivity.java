package com.example.shirley.selectdorm.selectdormitory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shirley.selectdorm.R;

public class ResultActivity extends Activity implements View.OnClickListener {
    private ImageView reHomeTv;
    private ImageView reResultPivTv;
    private TextView reHintTv;
    private Button reBackTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        init();
    }
    void init() {
        reHomeTv = (ImageView)findViewById(R.id.re_home);
        reHomeTv.setOnClickListener(this);
        reResultPivTv = (ImageView)findViewById(R.id.re_result_pic);
        reHintTv = (TextView)findViewById(R.id.re_hint);
        reBackTv = (Button)findViewById(R.id.re_back);
        reBackTv.setOnClickListener(this);
        Intent geti = getIntent();
        if(geti.getStringExtra("result").equals("success")) {
            reResultPivTv.setImageResource(R.drawable.correct);
        } else {
            reResultPivTv.setImageResource(R.drawable.error);
            reHintTv.setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.re_home:
            case R.id.re_back:
                Intent i = new Intent(ResultActivity.this, MainActivity.class);
                Intent geti = getIntent();
                i.putExtra("id", geti.getStringExtra("id"));
                startActivity(i);
                SelectDormitory.instance.finish();
                SelectType.instance.finish();
                finish();
                break;
            default:
                break;
        }
    }
}
