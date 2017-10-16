package cn.ezandroid.ezpermission.demo;

import android.Manifest;
import android.os.Bundle;
import android.support.v13.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import cn.ezandroid.ezpermission.EZPermission;
import cn.ezandroid.ezpermission.PermissionCallback;
import cn.ezandroid.ezpermission.PermissionGroup;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        $(R.id.hasPermissions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int v1 = ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA);
                boolean v2 = EZPermission.hasPermissions(MainActivity.this,
                        PermissionGroup.CAMERA.getPermissions());
                Log.e("MainActivity", "hasPermissions:" + v1 + " " + v2);
            }
        });

        $(R.id.checkPermissions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EZPermission.permissions(PermissionGroup.CAMERA, PermissionGroup.MICROPHONE, PermissionGroup.STORAGE).callback(new PermissionCallback() {
                    @Override
                    public void onPermissionsGranted(String[] grantPermissions) {
                        Log.e("MainActivity", "onPermissionsGranted");
                    }

                    @Override
                    public void onPermissionsDenied(String[] deniedPermissions) {
                        Log.e("MainActivity", "onPermissionsDenied");
                    }
                }).check(MainActivity.this);
            }
        });
    }
}
