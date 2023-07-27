package com.example.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ManHinhChao extends AppCompatActivity {

//    ImageView imageView;
    EditText ed_msv;
    Button btn_msv;
    TextView tv_tbao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
//        imageView = findViewById(R.id.img_chao);
//
//        Glide.with(this).load(R.drawable.mhchao).into(imageView);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(ManHinhChao.this, LoginActivity.class));
//            }
//        }, 5000);
        ed_msv = findViewById(R.id.ed_msvien);
        btn_msv = findViewById(R.id.btn_save);
        tv_tbao = findViewById(R.id.tv_tbao);
        tv_tbao.setVisibility(View.INVISIBLE);
        btn_msv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = ed_msv.getText().toString();
                if (ma.equals("PH23613")){
                    startActivity(new Intent(ManHinhChao.this, MainActivity.class));
                }else {
                    tv_tbao.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}