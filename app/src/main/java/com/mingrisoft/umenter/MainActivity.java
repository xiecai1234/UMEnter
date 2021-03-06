package com.mingrisoft.umenter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public ArrayList<SnsPlatform> platforms = new ArrayList<SnsPlatform>();
    private SHARE_MEDIA[] list = {SHARE_MEDIA.QQ,SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN};
    private ImageView QQ,WeiXin,Sina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_enter_activity);
        initPlatforms();
        init();
    }

    private void init() {
        QQ = (ImageView) findViewById(R.id.iv_qq_login);
        WeiXin = (ImageView) findViewById(R.id.iv_weixin_login);
        Sina = (ImageView) findViewById(R.id.iv_sina_login);
        QQ.setOnClickListener(this);
        WeiXin.setOnClickListener(this);
        Sina.setOnClickListener(this);
    }

    private void initPlatforms(){
        platforms.clear();
        for (SHARE_MEDIA e :list) {
            if (!e.toString().equals(SHARE_MEDIA.GENERIC.toString())){
                platforms.add(e.toSnsPlatform());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_qq_login:
                UmEnter(0);
                break;
            case R.id.iv_weixin_login:
                UmEnter(2);
                break;
            case R.id.iv_sina_login:
                UmEnter(1);
                break;
        }
    }

    private void UmEnter(int pos) {
        final boolean isauth = UMShareAPI.get(this).isAuthorize((Activity) this,platforms.get(pos).mPlatform);
        if (isauth){

            UMShareAPI.get(this).deleteOauth((Activity) this,platforms.get(pos).mPlatform,authListener);

        }else {
            UMShareAPI.get(this).doOauthVerify((Activity) this,platforms.get(pos).mPlatform,authListener);

        }
    }
    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(MainActivity.this,"成功了",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(MainActivity.this,"失败："+t.getMessage(),Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(MainActivity.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        mShareAPI.onActivityResult(requestCode, resultCode, data);
//    }
}
