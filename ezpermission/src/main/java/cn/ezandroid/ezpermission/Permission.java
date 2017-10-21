package cn.ezandroid.ezpermission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限组枚举
 *
 * @author like
 * @date 2017-09-28
 */
public enum Permission {

    CALENDAR(Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR),
    CAMERA(Manifest.permission.CAMERA),
    CONTACTS(Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS),
    LOCATION(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION),
    MICROPHONE(Manifest.permission.RECORD_AUDIO),
    PHONE(Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS),
    SENSORS("android.permission.BODY_SENSORS"), // Manifest.permission.BODY_SENSORS api>=20
    SMS(Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS),
    STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE);

    private String[] mPermissions;

    private Permission(String... permission) {
        this.mPermissions = permission;
    }

    public String[] getPermissions() {
        return mPermissions;
    }

    /**
     * 权限是否可用
     *
     * @param context
     * @return
     */
    public boolean available(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        for (String permission : mPermissions) {
            int result = ContextCompat.checkSelfPermission(context, permission);
            if (result != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }

    /**
     * 申请权限
     *
     * @param context
     */
    public void apply(Context context, PermissionCallback callback) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (callback != null) {
                callback.onPermissionsGranted(mPermissions);
            }
        } else {
            String[] deniedPermissions = getDeniedPermissions(context, mPermissions);
            if (deniedPermissions.length > 0) {
                ProxyActivity.launch(context, deniedPermissions, callback);
            } else {
                if (callback != null) {
                    callback.onPermissionsGranted(mPermissions);
                }
            }
        }
    }

    private static String[] getDeniedPermissions(Context context, String... permissions) {
        List<String> deniedList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedList.add(permission);
            }
        }
        return deniedList.toArray(new String[deniedList.size()]);
    }
}