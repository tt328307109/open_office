package cn.melonmobile.open_office;

import android.annotation.SuppressLint;
import android.graphics.PixelFormat;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.smtt.sdk.TbsReaderView;
import com.tencent.smtt.sdk.WebView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TbsReaderView.ReaderCallback {

    Button btn1, btn2, btn3, btn4;
    TbsReaderView readerView;
    RelativeLayout mRelativeLayout;
    RxPermissions rxPermissions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //这个对宿主没什么影响，建议声明
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mRelativeLayout = (RelativeLayout)findViewById(R.id.tbsView);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        readerView = new TbsReaderView(this, this);
        mRelativeLayout.addView(readerView,new RelativeLayout.LayoutParams(-1,-1));
        TbsReaderView.ReaderCallback readerCallback = new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {

            }
        };
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
    String mpath = Environment.getExternalStorageDirectory().getAbsolutePath();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                openFile(mpath+ File.separator+"test01"+File.separator+"myword.docx");
                break;
            case R.id.btn2:
                openFile(mpath+ File.separator+"test01"+File.separator+"myexcel.xlsx");
                break;
            case R.id.btn3:
                openFile(mpath+ File.separator+"test01"+File.separator+"myppt.pptx");
                break;
            case R.id.btn4:
                openFile(mpath+ File.separator+"test01"+File.separator+"mypdf.pdf");
                break;
                default:
                    break;
        }
    }


    private void openFile(String path){
        Bundle bundle = new Bundle();
        //文件路径
        bundle.putString("filePath", path);
        //临时文件的路径，必须设置，否则会报错
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getAbsolutePath()+"腾讯文件TBS");
        //准备
        boolean result = readerView.preOpen(getFileType(path), false);
        if (result) {
            //预览文件
            readerView.openFile(bundle);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        readerView.onStop();
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Log.d("print", "paramString---->null");
            return str;
        }
        Log.d("print", "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d("print", "i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        Log.d("print", "paramString.substring(i + 1)------>" + str);
        return str;
    }

}
