package cn.ezandroid.lib.ezpermission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * 权限申请回调
 *
 * @author like
 * @date 2017-09-28
 */
public interface PermissionCallback {

    /**
     * 权限申请成功
     *
     * @param grantedPermissions 申请成功的权限
     */
    default void onPermissionGranted(String[] grantedPermissions) {}

    /**
     * 权限申请失败
     *
     * @param deniedPermissions   申请失败的权限
     * @param isNoLongerPrompted 是否不再提示
     */
    default void onPermissionDenied(String[] deniedPermissions, boolean isNoLongerPrompted) {}

    /**
     * 权限列表全部申请成功
     */
    default void onAllPermissionsGranted() {}

    /**
     * 打开权限设置页
     */
    default void onStartSetting(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 非Activity的Context必须加此参数
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // try住异常，以防找不到应用设置页的情况
        }
    }
}
