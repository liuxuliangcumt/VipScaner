package com.cumt.vipscaner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cumt.vipscaner.util.DisplayUtil;
import com.cumt.vipscaner.util.PermissionUtil;
import com.example.qrcode.Constant;
import com.example.qrcode.ScannerActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements PermissionUtil.OnRequestPermissionsResultCallbacks {
    public MainActivity() {
        permissionsMap.put(Manifest.permission.CAMERA, R.string.permission_photo);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_scan = findViewById(R.id.tv_scan);
        tv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                // 设置扫码框的宽
                intent.putExtra(Constant.EXTRA_SCANNER_FRAME_WIDTH, DisplayUtil.dip2px(MainActivity.this, 200));
                // 设置扫码框的高
                intent.putExtra(Constant.EXTRA_SCANNER_FRAME_HEIGHT, DisplayUtil.dip2px(MainActivity.this, 200));
                // 设置扫码框距顶部的位置
                intent.putExtra(Constant.EXTRA_SCANNER_FRAME_TOP_PADDING, DisplayUtil.dip2px(MainActivity.this, 100));
                // 可以从相册获取
                intent.putExtra(Constant.EXTRA_IS_ENABLE_SCAN_FROM_PIC, true);
                startActivityForResult(intent, 888);
            }
        });
        requestPermissions();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 888:
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null || data.getExtras() == null) {
                        return;
                    }
                    String result = data.getExtras().getString(Constant.EXTRA_RESULT_CONTENT);
                    Log.e("zq", "二维码扫描结果：" + result);
                    if (TextUtils.isEmpty(result)) {
                        return;
                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private static final int REQUEST_CODE = 0;
    private final Map<String, Integer> permissionsMap = new HashMap<>();
    private boolean requestPermissions() {
        return PermissionUtil.requestPermissions(this, REQUEST_CODE, permissionsMap.keySet().toArray(new String[]{}));
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms, boolean isAllGranted) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms, boolean isAllDenied) {

    }
}
