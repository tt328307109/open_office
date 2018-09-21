package cn.melonmobile.open_office;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.smtt.sdk.TbsVideo;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1;
    RxPermissions rxPermissions;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        requestSomePerMissions();
    }

    @SuppressLint("CheckResult")
    private void requestSomePerMissions() {
        rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> { // will emit 2 Permission objects
                    if (permission.granted) {
                        // `permission.name` is granted !
                    } else {
                        // Denied permission with ask never again
//                         Need to go to the settings
//                        ArmsUtils.exitApp();
                    }
                });
    }
    @Override
    public void onClick(View v) {
        if(TbsVideo.canUseTbsPlayer(getApplicationContext())){
            TbsVideo.openVideo(getApplicationContext(), url);
        }
    }
    String url = "http://221.228.226.23/11/t/j/v/b/tjvbwspwhqdmgouolposcsfafpedmb/sh.yinyuetai.com/691201536EE4912BF7E4F1E2C67B8119.mp4";

}
