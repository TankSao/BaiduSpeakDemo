package com.example.administrator.baiduspeakdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private EditText et;
    private SpeechSynthesizer mSpeechSynthesizer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestAllPower();//申请权限
        initTTs();//百度语音初始化
        et = (EditText) findViewById(R.id.et_input);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak(et.getText().toString());
            }
        });
    }

    private void speak(String msg) {
        if(mSpeechSynthesizer==null){
            Toast.makeText(MainActivity.this,"语音合成初始化失败",Toast.LENGTH_SHORT).show();
        }else{
            if(TextUtils.isEmpty(msg)){
                mSpeechSynthesizer.speak("输入内容为空");
            }else{
                mSpeechSynthesizer.speak(msg);
            }
        }
    }

    private void initTTs() {
        TtsMode ttsMode = TtsMode.ONLINE;
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(this);
        mSpeechSynthesizer.setAppId(Config.appId);
        mSpeechSynthesizer.setApiKey(Config.appKey, Config.secretKey);
        //设置声音：0女声，1男声，2男声，3,情感男声，4童声
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        //音量大小【0,15】
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
        //语速快慢【0,9】
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
        //语调【0,9】越大越尖锐
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        mSpeechSynthesizer.setAudioStreamType(AudioManager.MODE_IN_CALL);
        mSpeechSynthesizer.initTts(ttsMode);
    }

    public void requestAllPower() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET,
                                Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_PHONE_STATE}, 1);
            }
        }
    }
}
