package cn.ezandroid.ezpermission;

import android.Manifest;

/**
 * 权限组枚举
 *
 * @author like
 * @date 2017-09-28
 */
public enum PermissionGroup {

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

    private String[] mPermission;

    private PermissionGroup(String... permission) {
        mPermission = permission;
    }

    public String[] getPermissions() {
        return mPermission;
    }
}