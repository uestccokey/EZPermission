package cn.ezandroid.ezpermisson;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

import cn.ezandroid.lib.ezpermission.EZPermission;
import cn.ezandroid.lib.ezpermission.Permission;
import cn.ezandroid.lib.ezpermission.PermissionCallback;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        $(R.id.hasPermissions).setOnClickListener(view -> {
            boolean v1 = EZPermission.permissions(Permission.STORAGE)
                    .available(MainActivity.this);
            boolean v2 = EZPermission.permissions(Permission.CAMERA)
                    .available(MainActivity.this);
            Log.e("MainActivity", "hasPermissions:" + v1 + " " + v2);
        });

        $(R.id.checkPermissions).setOnClickListener(view -> {
            Permission storage = new Permission(Permission.STORAGE);
            Permission readPhoneState = new Permission(Permission.CAMERA);
            EZPermission.permissions(storage, readPhoneState)
                    .apply(MainActivity.this, new PermissionCallback() {
                        @Override
                        public void onPermissionGranted(String[] grantedPermissions) {
                            Log.e("MainActivity", "onPermissionGranted:" + Arrays.toString(grantedPermissions));
                        }

                        @Override
                        public void onPermissionDenied(String[] deniedPermissions, boolean isNoLongerPrompted) {
                            Log.e("MainActivity", "onPermissionDenied:" + Arrays.toString(deniedPermissions) + " " + isNoLongerPrompted);
                        }

                        @Override
                        public void onAllPermissionsGranted() {
                            Log.e("MainActivity", "onAllPermissionsGranted");
                        }

                        @Override
                        public void onStartSetting(Context context) {
                            Log.e("MainActivity", "onStartSetting");
                        }
                    });
        });
    }
}
