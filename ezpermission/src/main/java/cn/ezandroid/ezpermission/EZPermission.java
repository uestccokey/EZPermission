package cn.ezandroid.ezpermission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.ezandroid.ezpermission.ProxyActivity.PermissionListener;

/**
 * 权限管理
 *
 * @author like
 * @date 2017-09-28
 */
public class EZPermission {

    private EZPermission() {
    }

    /**
     * 是否拥有该权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        return hasPermission(context, Arrays.asList(permissions));
    }

    /**
     * 是否拥有该权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean hasPermission(Context context, List<String> permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(context, permission);
            if (result != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }

    public static Builder permissions(PermissionGroup... groups) {
        return new Builder(groups);
    }

    public static class Builder {

        private String[] mPermissions;

        private PermissionListener mPermissionListener;

        private PermissionCallback mPermissionCallback;

        private Builder(PermissionGroup... groups) {
            List<String> permissionList = new ArrayList<>();
            for (PermissionGroup group : groups) {
                permissionList.addAll(Arrays.asList(group.getPermissions()));
            }
            this.mPermissions = permissionList.toArray(new String[permissionList.size()]);
            this.mPermissionListener = new ProxyActivity.PermissionListener() {

                @Override
                public void onRequestPermissionsResult(String[] permissions, int[] grantResults) {
                    List<String> deniedList = new ArrayList<>();
                    for (int i = 0; i < permissions.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            deniedList.add(permissions[i]);
                        }
                    }
                    if (deniedList.isEmpty()) {
                        onPermissionsGranted();
                    } else {
                        onPermissionsDenied(deniedList);
                    }
                }
            };
        }

        public Builder callback(PermissionCallback callback) {
            this.mPermissionCallback = callback;
            return this;
        }

        public Builder rationale() {
            // TODO
            return this;
        }

        public void check(Context context) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                onPermissionsGranted();
            } else {
                String[] deniedPermissions = getDeniedPermissions(context, mPermissions);
                if (deniedPermissions.length > 0) {
                    ProxyActivity.launch(context, deniedPermissions, mPermissionListener);
                } else {
                    onPermissionsGranted();
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

        private void onPermissionsGranted() {
            if (mPermissionCallback != null) {
                mPermissionCallback.onPermissionsGranted(mPermissions);
            }
        }

        private void onPermissionsDenied(List<String> deniedList) {
            if (mPermissionCallback != null) {
                mPermissionCallback.onPermissionsDenied(deniedList.toArray(new String[deniedList.size()]));
            }
        }
    }
}
