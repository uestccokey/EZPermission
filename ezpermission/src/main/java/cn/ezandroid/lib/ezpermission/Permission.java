package cn.ezandroid.lib.ezpermission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限模型
 *
 * @author like
 * @date 2017-09-28
 */
public class Permission implements Serializable {

    private static final long serialVersionUID = 42L;

    // 权限组常量 兼容Android O
    public static final String[] CALENDAR = new String[]{Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR};
    public static final String[] CAMERA = new String[]{Manifest.permission.CAMERA};
    public static final String[] CONTACTS = new String[]{Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS};
    public static final String[] LOCATION = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    public static final String[] MICROPHONE = new String[]{Manifest.permission.RECORD_AUDIO};
    public static final String[] PHONE = new String[]{Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            "android.permission.READ_CALL_LOG", // Manifest.permission.READ_CALL_LOG api>=16
            "android.permission.WRITE_CALL_LOG", // Manifest.permission.WRITE_CALL_LOG api>=16
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS};
    public static final String[] SENSORS = new String[]{"android.permission.BODY_SENSORS"}; // Manifest.permission.BODY_SENSORS api>=20
    public static final String[] SMS = new String[]{Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS};
    public static final String[] STORAGE = new String[]{"android.permission.READ_EXTERNAL_STORAGE", // Manifest.permission.READ_EXTERNAL_STORAGE api>=16
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private final String[] mPermissions;

    public Permission(String... permission) {
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
     * @param callback
     */
    public void apply(Context context, PermissionCallback callback) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (callback != null) {
                callback.onPermissionGranted(mPermissions);
            }
        } else {
            String[] deniedPermissions = getDeniedPermissions(context, mPermissions);
            if (deniedPermissions.length > 0) {
                PermissionProxyActivity.launch(context, this, callback);
            } else {
                if (callback != null) {
                    callback.onPermissionGranted(mPermissions);
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
        return deniedList.toArray(new String[0]);
    }

    @NonNull
    @Override
    public String toString() {
        return "Permission{" +
                "mPermissions=" + Arrays.toString(mPermissions) +
                '}';
    }
}