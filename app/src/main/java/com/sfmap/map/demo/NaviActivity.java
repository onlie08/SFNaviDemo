package com.sfmap.map.demo;

import android.os.Bundle;
import android.os.Handler;

import com.sfmap.api.navi.Navi;
import com.sfmap.api.navi.NaviView;

public class NaviActivity extends NaviBaseActivity{
    private NaviView mNaviView;
    private Navi mNavi;
    private SFSpeechSyntesizer sfSpeechSyntesizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);
        sfSpeechSyntesizer = SFSpeechSyntesizer.getInstance(NaviActivity.this);
        initView(savedInstanceState);
        initNaviData();
    }

    private void initView(Bundle savedInstanceState) {
        mNaviView = findViewById(R.id.navi_view);
        mNaviView.setMapNaviViewListener(this);
        mNaviView.onCreate(savedInstanceState);
        mNaviView.getMap().getUiSettings().setCompassEnabled(false);
    }

    private void initNaviData() {
        mNavi = Navi.getInstance(this);
        mNavi.addNaviListener(this);
        mNavi.setSoTimeout(15000);
        mNavi.setEmulatorNaviSpeed(100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mNavi.getNaviPath()!=null){
                    mNavi.startNavi(Navi.EmulatorNaviMode);
                }
            }
        },2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNaviView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            mNavi.stopNavi();
            mNaviView.onDestroy();
            sfSpeechSyntesizer.destroy();
        }catch (Exception e){

        }
    }
}
