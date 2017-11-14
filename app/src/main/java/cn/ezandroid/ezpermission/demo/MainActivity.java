package cn.ezandroid.ezpermission.demo;

import android.Manifest;
import android.os.Bundle;
import android.support.v13.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import cn.ezandroid.ezpermission.EZPermission;
import cn.ezandroid.ezpermission.Permission;
import cn.ezandroid.ezpermission.PermissionCallback;

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
                boolean v2 = EZPermission.permissions(Permission.CAMERA)
                        .available(MainActivity.this);
                Log.e("MainActivity", "hasPermissions:" + v1 + " " + v2);
            }
        });

        $(R.id.checkPermissions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EZPermission.permissions(Permission.CAMERA, Permission.MICROPHONE, Permission.STORAGE)
                        .apply(MainActivity.this, new PermissionCallback() {
                            @Override
                            public void onPermissionGranted(Permission grantedPermission) {
                                Log.e("MainActivity", "onPermissionGranted:" + grantedPermission.name()
                                        + " " + grantedPermission.available(MainActivity.this));
                            }

                            @Override
                            public void onPermissionDenied(Permission deniedPermission) {
                                Log.e("MainActivity", "onPermissionDenied:" + deniedPermission.name()
                                        + " " + deniedPermission.available(MainActivity.this));
                            }

                            @Override
                            public void onAllPermissionsGranted() {
                                Log.e("MainActivity", "onAllPermissionsGranted");
                            }
                        });
            }
        });
    }
}
